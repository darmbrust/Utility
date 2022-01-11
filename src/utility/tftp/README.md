### Multithreaded Multiclient Java TFTP Server

At another point in time, I needed a TFTP server I could embed into some java software. I couldn't find one anywhere... at least, not one that worked, and had a 
reasonable license.

So, I wrote my own. Below is a single-class implementation of a fully multithreaded TFTP server. This server will handle multiple clients at the same time. 
This implements RFC 1350 and wrapping block numbers for large file support (I don't know if this is in a RFC, or if it is just something that most clients do)

This implementation depends on Apache Commons Net and Log4J.

To launch, just create an instance of the class. An IOException will be thrown if the server fails to start for reasons such as port in use, port denied, etc.
To stop, use the shutdown method.
To check to see if the server is still running (or if it stopped because of an error), call the isRunning() method.

I tried to give this code back to Apache Commons Net, but the powers that be there refuse to allow any server-like code to be part of the release. 
They only want to be client code. (Maybe you should change your name to Apache Commons Client Net then...)

Never mind the fact that their own TFTP client was horribly broken for years because they didn't have any automated tests, because they didn't have a server. 
In the end, the powers that be [took](https://gitbox.apache.org/repos/asf?p=commons-net.git;a=blob;f=src/test/java/org/apache/commons/net/tftp/TFTPServer.java;hb=HEAD)
 the code, and buried it in their test path. I don't know if they have maintained it. I recommend you use my implementation. It is part of commercial software in use 
 every day, so it is tested and maintained as necessary.

Note, I also have JUnit tests for this code - but I'll just point you at the apache test code for those.


[Path Tests](https://gitbox.apache.org/repos/asf?p=commons-net.git;a=blob;f=src/test/java/org/apache/commons/net/tftp/TFTPServerPathTest.java;hb=HEAD)

[Transfer Tests](https://gitbox.apache.org/repos/asf?p=commons-net.git;a=blob;f=src/test/java/org/apache/commons/net/tftp/TFTPTest.java;hb=HEAD)
