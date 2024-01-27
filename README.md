# COP4520-Assignment1

Sebastien Joseph

The prime number formula was the simplest part. I had prior experience with leetcode so I was able to solve that portion with a relatively good execution time. I used Sieve of Eratosthenes, or at least a variation of it since the actual formula wouldn't work 1 to 1 with this algorithm with threads.
The threading on the other hand was harder to work with. I originally used the Thread type but would have trouble getting the execution time below 34 seconds so I consulted the internet and found out about ExecutorService which would help manage the threads by reusing threads in order to save on processing and memory used.
To check the earlier numbers, I looked up a list of prime numbers and was able to get up to that list, I had no way of checking the accuracy of the higher numbers, I'm assuming since the lower numbers were accurate, the higher numbers would also be accurate.
