This project focused on applying graph traversal algorithms that were learned in CSIntsy
to solve the infamous game Sokoban. Sokoban in the computing world is known for its 
large state space, so even if your program could theoretically travel through all the 
states it wouldn't necessarily do so in a reasonable amount of time. Because of this
my group made use of techniques such as manhattan distance as our heursitic, which 
allowed for a more intelligent travel through the state space; we also used simple
a simple corner deadlock checker, which eliminated the need of checking "lost cause"
states; and we made use of push states where only a change in box position would be 
considered it's own state, this limited the state space the bot needs to explore.
