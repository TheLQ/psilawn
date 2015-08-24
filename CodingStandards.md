## Intro ##

Yes, everyone needs standards. And since I'm about to forget them in 5 minutes, lets write them down.

## Details ##

Syntax
  * All code needs to be readable. Don't do fancy things
  * Minimize code duplication. Use variables if something is going to be used more than once
  * Do not have really long lines with methods inside of methods inside of constructors inside of parameters inside of something else. This A) Looks like crap and B) Is hard to read
  * (Might be a java only thing) Don't nest classes if its going to be used by something else.
  * Use Packages (Java) or the C equivalent to help separate code

Documentation
  * Until I can find a C equivelent, use JavaDoc annotation standard (http://java.sun.com/j2se/javadoc/writingdoccomments/)
  * ALL methods, classes, and instance variables need to be DOCUMENTED
  * Update soon-to-be Logic page in wiki to describe whats going on

Commits
  * Commit OFTEN - Massive commit messages should only be reserved for very early development when one thing can't be written without another thing written as well
  * Do NOT commit un-compilable code unless you say its a checkpoint
  * On the inverse, do not commit for ever little thing.

Well I think this just about sums it up.