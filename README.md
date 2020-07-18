# Algorithms and DS
Excercises for princeton algorithm course from coursera
# Algorithm-I
## WK-I: Dynamic connectivity problem
----
### Assignment: Finding percolation threshold: *Refer to [specs](./doc/1-Percolation-Assignment.pdf)*

### Question 1 Social network connectivity. 
Given a social network containing n members and a log file containing m timestamps at which times pairs of members formed friendships, design an algorithm to determine
the earliest time at which all members are connected (i.e., every member is a friend of a friend of a friend ... of a friend). Assume that the log file is sorted by timestamp 
and that friendship is an equivalence relation. The running time of your algorithm should be `m log n` or better and use extra space proportional to n.

### Question 2 Union-find with specific canonical element. 
Add a method `find()` to the union-find data type so that `find(i)` returns the largest element in the connected component containing i. The operations, `union()`,
`connected()`, and `find()` should all take logarithmic time or better.For example, if one of the connected components is **{1,2,6,9}**, then the `find()` method should return 9
for each of the four elements in the connected components.

### Question 3 Successor with delete. 
Given a set of n integers **S={0,1,...,n−1}** and a sequence of requests of the following form:
- Remove x from S
- Find the successor of x: the smallest y in S such that `y≥x`.

design a data type so that all operations (except construction) take logarithmic time or better in the worst case.

### Question 4 3-SUM in quadratic time. 
Design an algorithm for the 3-SUM problem that takes time proportional to `n^2` in the worst case. You may assume that you can sort the nn integers in time proportional to `n^2` or better.

Hint: given an integer `x` and a sorted array `a[]` of `n` distinct integers, design a linear-time algorithm to determine if there exists two distinct indices `i` and `j` such that **a[i] + a[j] == x**.

### Question 5 Search in a bitonic array. 
An array is bitonic if it is comprised of an increasing sequence of integers followed immediately by a decreasing sequence of integers. Write a program that, given a bitonic array of nn distinct integer values, determines whether a given integer is in the array.

- Standard version: Use `∼ 3 lg n` compares in the worst case.
- Signing bonus: Use `∼ 2 lg n` compares in the worst case (and prove that no algorithm can guarantee to perform fewer than `∼ 2 lg n` compares in the worst case).

Hints: Standard version. First, find the maximum integer using `∼1 lg n` compares—this divides the array into the increasing and decreasing pieces.

Signing bonus. Do it without finding the maximum integer.

### Question 6 Egg drop. 
Suppose that you have an nn-story building (with floors 1 through n) and plenty of eggs. An egg breaks if it is dropped from floor T or higher and does not break otherwise. Your goal is to devise a strategy to determine the value of T given the following limitations on the number of eggs and tosses:

- Version 0: 1 egg, `≤T` tosses.
- Version 1: `∼ 1 lg n` eggs and `∼ 1 lg n` tosses.
- Version 2: `∼ lg T` eggs and `∼ 2 lg T` tosses.
- Version 3: 2 eggs and 2 sqrt(n) tosses.
- Version 4: 22 eggs and ≤c sqrt(T) tosses for some fixed constant c.

Hints:

- Version 0: sequential search.
- Version 1: binary search.
- Version 2: find an interval containing T of size `≤2T`, then do binary search.
- Version 3: find an interval of size sqrt(n), then do sequential search. Note: can be improved to `∼ 2 sqrt(n)` tosses.
- Version 4: **1 + 2 + 3 + ... + t = 1/2 t^2**, Aim for `c = 2 sqrt(2)`.




## WK-II: Stacks-Queue and Elementry sorts
----
### Assignment: Deque and Randomized queue: *Refer to [specs](./doc/2-Queues-Assignment.pdf)*

### Question 1 Queue with two stacks. 
Implement a queue with two stacks so that each queue operations takes a constant amortized number of stack operations.

Hint: If you push elements onto a stack and then pop them all, they appear in reverse order. If you repeat this process, they're now back in order.

### Question 2 Stack with max.
Create a data structure that efficiently supports the stack operations (push and pop) and also a return-the-maximum operation. Assume the elements are real numbers so that you can compare them.

Hint: Use two stacks, one to store all of the items and a second stack to store the maximums.

### Question 3 Java generics. 
Explain why Java prohibits generic array creation.

Hint: to start, you need to understand that Java arrays are covariant but Java generics are not: that is, `String[]` is a subtype of `Object[]`, but `Stack<String>` is not a subtype of `Stack<Object>`.

### Question 4 Intersection of two sets. 
Given two arrays `a[]` and `b[]`, each containing nn distinct 2D points in the plane, design a subquadratic algorithm to count the number of points that are contained both in array `a[]` and array `b[]`.

Hint: shellsort (or any other subquadratic sort).

### Question 5 Permutation. 
Given two integer arrays of size `n`, design a subquadratic algorithm to determine whether one is a permutation of the other. That is, do they contain exactly the same entries but, possibly, in a different order.

Hint: sort both arrays

### Question 6 Dutch national flag. 
Given an array of n buckets, each containing a red, white, or blue pebble, sort them by color. The allowed operations are:
- `swap(i,j)`: swap the pebble in bucket `i` with the pebble in bucket `j`.
- `color(i)`: determine the color of the pebble in bucket `i`.
The performance requirements are as follows:
- At most `n` calls to `color()`.
- At most `n` calls to `swap()`.
- Constant extra space.

Hint: 3-way partitioning.

## WK-III 
----
### Assignment: Collinear points *Refer to [specs](./doc/3-Collinear-Points-Assignment.pdf)* 




