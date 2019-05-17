Warren Quattrocchi
CSC 130

1)Who is in your group? 

I did the project alone, so just me

2) How long did the project take?

This project took me about a whole day to complete the skeleton of, and the rest of the week to
implement all of the required/optional tasks mentioned in the requirements.
	The hardest part about this project was definitely understanding how the hash table was intended
to work with the provided DataCounter interface. One I figured out that the incCount method could
be used to replace the "put" method usually used in a hash table it became a matter of modifying the
old AVL tree we used in project 2 and coding the main method to read out the total counts

3) Before you started, which data structure did you expect would be the fastest?

I expected the hash table to be the fasted by far, due to the effeciency of inserting elements a hash
table has

4) Which data structure is the fastest? Why were you right or wrong?

The hash table was quicker than the 2 tree implementations, but barely. coming in at around 330 ms 
vs the BST's time of 350 ms. (These times varied so much that I'm not even sure if I would say the
hash table implementation was definitely faster).

5) In general, which DataCounter dictionary implementation was "better": trees or hash
tables? Note that you will need to define "better" (ease of coding, ease of debugging,
memory usage, disk access patterns, runtime for average input, runtime for all input, etc).

The AVL tree was definitely the easiest to code, as we had previously made on for project 2. 
Ignoring that however, the hash table was easier, as it was essentially just an array of linkedLists
that was very straightforward to code, unlike the rotations of an AVL tree which I found much more difficult
to deal with the first time around. The hash Table also seemed to make more sense for what the project
was for, such as incrementing a word count on a collision.

6)Are there cases in which a particular data structure performs really well or badly in the
correlator? Enumerate the cases for each data structure

All of the data structures performed the same in the correlator as they did in the WordCounter.

7)Give a one to two paragraph explanation of whether or not you think Bacon wrote
Shakespeare's plays based on the data you collected. No fancy statistical analysis here
(formal analysis comes later); keep it fun and simple.

I don't think that Bacon wrote Shakespeare's plays from the data that I collected in this project.
One reason is that when using the -numunique parameter, the difference in the 2 documents differed by
over 1000 words, showing a clear difference in vocabulary used. Another reason is that after running 
each word through normalization and only printing words that are used <1% & >.01 of total words, 
many of them were different enough when comparing the bulk (ignoring the top 5-ish words). To me this
did not show a direct correlation between the 2 articles. Third, my Euclidean Distance generated from
the DocumentCorrelator was 2,306,426, showing a large difference between the 2 documents.

8)Benchmarking was optional for us, but I did Implement some timers in the code to see how each
data structure performed. 

9)What did you enjoy about this assignment? What did you hate? Could we have done
anything better?
 
I think this assignment would be better if the requirements were stated much more directly, as 
I had a lot of confusion figuring out if I was done and ready to turn the project in.