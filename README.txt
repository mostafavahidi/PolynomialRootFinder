Hello and welcome to my Polynomial root finder program.
@author Mostafa Vahidi

To Start Program:

- In command prompt/line go to out -> production -> polRoot_Mostafa_Vahidi

- Then type in the following type of command:
	
	java polRoot [-newt, -sec, -hybrid] [-maxIt n] initP [initP2] polyFileName

	for example:
	
	java polRoot -newt 400 0 1 test.pol

- The solution will be put into {polynomialSolution.sol} file!

*Make sure your test.pol file is updated with correct polynomial vectors before running program!

By using this program, I was able to get 22 iterations for running bisection on one 
of the polynomials in the hw which should be the default one in test.pol when first running
program.