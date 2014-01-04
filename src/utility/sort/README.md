=== Natural Sorting in Java ===


A while ago, I had a need for more human friendly sorting within various software I maintain. Basically, what I wanted was a natural sort. One implementation that I found was the Alphanum algorithm, as discussed here: http://www.davekoelle.com/alphanum.html

But, it turned out his implementation didn't handle leading 0's properly, among other problems. I fixed the leading 0's problem, and a few other issues. He never responded when I tried to send him code updates, so apparently his page is dormant. I figured I might as well share my solution in case someone else needs it. 