# OWAT
"Obfuscation With A Twist", a protocol for obfuscating and un-obfuscating data inspired by the Rubik's Cube.

This obfuscation is accomplished through scrambling the data, in a series of different moves. Descrambling is accomplished through repeating those moves in reverse.

This idea is based on the fact that if you have no way to verify that any state of the data is correct, one cannot know what state is the correct one, therefore effectively hiding the data.

Use cases for this process include any application where you can separate the scrambled data and the key. For example, burning two CD's, writing the scrambled data to one and the key to the other.

Essentially, the process is simple. The data to be obfuscated is placed into a matrix, with each bit or byte placed into a point on that matrix. Then, the matrix is grown by adding rows and columns of dummy data in order to increase the amount of possible entropy. Once the data is set up, the data is scrambled, and the steps to scramble the data are recorded. The scrambled data is outputted to a ".obfd" (obfuscated data) file, and 'key' (list of scrambling moves, other bookkeeping data) is outputted to a ".obfdk" (obfuscated data key) file. In order to unscramble, the program reads in the scrambled data, recreates the scrambled matrix, and uses the key file to unscramble the data. The unscrambled data is then outputted back to the user.

### The goal of this process is not to be efficient in memory or processing time. The goal is to effectively obscure and hide data.
