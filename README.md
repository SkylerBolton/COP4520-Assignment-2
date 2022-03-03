# COP4520-Assignment-2

Problem 1:

RUNNING:

javac ProblemOne.java
java ProblemOne

In this problem, the showroom is represented by a synchronized block. The n (hardcoded as 100) guests are represented as threads competing for this lock. One of the threads is designated as the "leader" who is in charge of announcing when all guests have entered. Whenever a guest enters the showroom (acquires the lock), they will eat the cupcake ONLY if they have not eaten one previously, and if a cupcake is already present. Only when the leader enters the showroom and finds the cupcake gone will it be replaced, and the leader will increment their count. Threads will continue to compete for the lock until the leader has replaced the cupcake n times, and announces they are done. The threads competing for the lock results in a psuedorandom order of entry into the showroom. The runtime is rather slow, but this is an unavoidable consequence of the problem.

Problem 2:

RUNNING:

javac ProblemTwo.java
java ProblemTwo

I have decided to use approach #3, a queue. 1 and 2 allow for cases where a guest may not get into the room for a very long time, if at all. A queue guarentees a guest can eventually get in.

