/**
 * @(#)Main.java
 *
 * This file is part of Quackbot
 */

package psilawn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.commons.lang.StringUtils;

/**
 * Provides a GUI for bot
 *  -Output is formated and displayed
 *  -Can initate stop, start, and reload from here
 * @author Lord.Quackstar
 */
public class Main extends JFrame implements ActionListener {

	JTextPane errorLog;
	JScrollPane errorScroll;
	StyledDocument errorDoc;
	PrintStream oldOut,oldErr,newOut,newErr;

	/**
	 * Setup and display GUI, redirect output streams
	 */
	public Main() {
		/***Pre init, setup error log**/
		errorLog = new JTextPane();
		errorLog.setEditable(false);
		errorDoc = errorLog.getStyledDocument();
		errorScroll = new JScrollPane(errorLog);
		errorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		errorScroll.setMinimumSize(new Dimension(800,400));
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));

		oldOut = System.out;
		oldErr = System.err;
		System.setOut(newOut = new PrintStream(new FilteredStream(new ByteArrayOutputStream(),false)));
		System.setErr(newErr = new PrintStream(new FilteredStream(new ByteArrayOutputStream(),true)));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Will exit when close button is pressed
	 	setTitle("PSILawn Control Panel");
	 	setMinimumSize(new Dimension(1000,700));

	 	JPanel contentPane = new JPanel();
	 	contentPane.setLayout(new BorderLayout());

		//Create side bar
		JPanel sideBar = new JPanel();
		sideBar.add(new JLabel("Sidebar"));

		//Split body pane
		JSplitPane bodyPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		bodyPane.add(errorScroll);
		bodyPane.add(sideBar);
		bodyPane.setDividerLocation(0.2);
		
		contentPane.add(bodyPane,BorderLayout.CENTER);
		
	 	JPanel bottom = new JPanel();
	 	JButton cancel = new JButton("Button 1");
	 	cancel.addActionListener(this);
	 	bottom.add(cancel);
	 	JButton start = new JButton("Button 2");
	 	start.addActionListener(this);
	 	bottom.add(start);
	 	JButton reload = new JButton("Button 3");
	 	reload.addActionListener(this);
		bottom.add(reload);
		
		contentPane.add(bottom,BorderLayout.SOUTH);

		add(contentPane); //add to JFrame
		setVisible(true); //make JFrame visible
		
		System.out.println("Some output. Whoo, I work!");
	}
	
	/**
	 * Button action listener, controls for Controller
	 * @param e  Event
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
	}

	/**
	 * Output Wrapper, Redirects all ouput to log at bottom
	 */
	class FilteredStream extends FilterOutputStream {
		AttributeSet className, text;
		boolean error;

		/**
		 * Setup class: Call super() and set basic styles
		 * @param aStream
		 * @param error
		 */
		public FilteredStream(OutputStream aStream,boolean error) {
			super(aStream);
			this.error = error;

			Style style = errorLog.addStyle("Class", null);
			StyleConstants.setForeground(style, Color.blue );

			style = errorLog.addStyle("Normal", null);

			style = errorLog.addStyle("Error", null);
			StyleConstants.setForeground(style, Color.red);

			style = errorLog.addStyle("Send", null);
			StyleConstants.setForeground(style, Color.ORANGE);
		}

		@Override
		public void write(byte b[], int off, int len) throws IOException {
			try {
				//get string version
				String aString = new String(b , off , len).trim();

				//don't print empty strings
				if(aString.length()==0)
					return;

				//get calling class name
				StackTraceElement[] elem = Thread.currentThread().getStackTrace();
				String callingClass = null;
				if(elem[10].getClassName().equals("java.lang.Throwable"))
					callingClass = elem[12].getClassName();
				else
					callingClass = elem[10].getClassName();

				String[] splitClass = StringUtils.split(callingClass,".");
				callingClass = splitClass[splitClass.length-1];
				
				String[] endString = new String[4];
				endString[0] = "["+(new SimpleDateFormat("hh:mm:ss aa").format(new Date()))+"] ";
				endString[1] = callingClass+": ";
				endString[2] = aString;

				//Set style
				/* Unkown yet
				Style style = null;
				if(error) style = errorDoc.getStyle("Error");
				else if(endString[3].substring(0,3).equals(">>>")) style = errorDoc.getStyle("BotSend");
				else if(endString[3].substring(0,3).equals("###")) style = errorDoc.getStyle("Error");
				else style = errorDoc.getStyle("Normal");*/
				Style style = errorDoc.getStyle("Normal");

				errorDoc.insertString(errorDoc.getLength(),"\n",errorDoc.getStyle("Normal"));
				errorDoc.insertString(errorDoc.getLength(),endString[0],errorDoc.getStyle("Normal"));
				errorDoc.insertString(errorDoc.getLength(),endString[1],errorDoc.getStyle("Class"));
				errorDoc.insertString(errorDoc.getLength(),endString[2],style);

				if(error)
					oldErr.println(aString); //so runtime errors can be caught
				else
					oldOut.println(aString); //so runtime errors can be caught

				errorLog.repaint();
				errorLog.revalidate();
				errorLog.setCaretPosition(errorDoc.getLength());
			}
			catch (BadLocationException ble) {
				oldErr.println("Error");
			}
			catch(Exception e) {
				e.printStackTrace(oldErr);
			}
		}
	}

	/**
	 * Main method, starts Main
	 * @param args  Passed parameters. This is ignored
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
}