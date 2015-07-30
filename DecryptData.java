/*
		
	NOTE: The check-pairity matrix is based on the generating polynomial from the field K(7)
	which is g(x) = 1 + x + x^3 ---> g = 1 1 0 1 0 0 0
	then created the generator matrix from g
	
*/

import java.util.Scanner;
import java.util.Random;

class DecryptData 
{
	public static void main(String[] args)
	{
		
		// List of global variables for the EncryptData class
		int[][] H = { {1,1,0},{0,1,1},{1,1,1},{1,0,1},{1,0,0},{0,1,0},{0,0,1} };
		int[][] binMatrix;
		int[][] S;
		int[][] C;
		int[][] D;
		int numPasswords;
		
		System.out.println();
		System.out.println("*************************");
		System.out.println();
		System.out.println("\t  Scenario 1");
		System.out.println();
		System.out.println("*************************");
		
		
		// Lets the user input any string of lenght 13
//		String string;
		String string = "fl38Cd%mr.ypJ";

	
		// Creating the Scanner object to read user input
		Scanner keyboard = new Scanner(System.in);
		
		
		// Gets the user to enter any arbitrary password of any length
//		System.out.print("Enter a password: ");
//		string = keyboard.nextLine();

		// Ensures that the user enters a string of lenght 13
		while (string.length() != 13)
		{
			System.out.println();
			System.out.println("Error. Please enter a new password that is thirteen characters long!");
			System.out.println();
			System.out.println();

			// Gets the user to re-enter the password
//			System.out.print("Enter a password: ");	
//			string = keyboard.nextLine();
		}

		
		
		// Creating an array of strings of size strings length
		String[] binArray = new String [string.length()];
		
		
		// Creating a loop that will convert the string into binary
		for (int index = 0; index < string.length(); index++)
		{
			binArray[index] = Integer.toString(string.charAt(index),2);
				
		}
		
		// Finds the max length string in the array
		// @param: string array
		// @return: max length of a string in the array
		int max = findMax(binArray);
		
		// Returns an array of strings with the same length
		// @param: array of strings
		// @return: array of strings with all elements the same length
		String B[] = getSameLength(binArray,max);
		
		// Converts the array of strings into a binary matrix
		// @param: array of binary strings
		// @return: binary matrix of Integer type
		binMatrix = convertToMatrix(binArray);


		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Original Word: ");
		for (int r = 0; r < binMatrix.length; r++)
		{
			for (int c = 0; c < binMatrix[r].length; c++) 
			{
				System.out.print(binMatrix[r][c] + " ");
			}
			
			System.out.println();
		}
		
		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		// Multiplies the matrix of words with the check-pairity matrix to get the syndromes
		// @param: matrix of words. check-pairity matrix
		// @return: syndrome matrix (matrix with the possible errors)
		S = binaryMatrixMult(binMatrix,H);		

		// Prints out the check-pairity matrix
		System.out.println("Check-Parity Matrix:");
		for (int r = 0; r < H.length; r++)
		{
			for (int c = 0; c < H[r].length; c++) 
			{
				System.out.print(H[r][c] + " ");
			}
			
			System.out.println();
		}


		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		// Displays the Syndrome matrix of errors
		System.out.println("Syndome Matrix: ");
		for (int r = 0; r < S.length; r++)
		{
			for (int c = 0; c < S[r].length; c++) 
			{
				System.out.print(S[r][c] + " ");
			}
			
			System.out.println();
		}
		
		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();
	
		// Creating a method that will cross check the syndromes and check-pairity 
		// @param: syndrome matrix, check-pairity matrix
		// @return: an array of the lcoations where the errors are
		int[] errors = getErrors(H,S);
		System.out.print("Errors: " );
		for (int index = 0; index < errors.length; index++)
		{
			System.out.print(errors[index] + " ");
		}
		
		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		// Creating a method that will correct the errors
		// @param: array of indicies, matrix of syndromes
		// @return matrix of corrected words
		C = correctErrors(errors,binMatrix);
		System.out.println("Corrected Word: ");
		for (int i = 0; i < C.length; i++)
		{
			for (int j = 0; j < C[i].length; j++)
			{
				System.out.print(C[i][j] + " ");
			}
			
			System.out.println();
		}
		
		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();
		
		// Calls a method that will multiply the matricies together to check if errors were correct
		int[][] synd = binaryMatrixMult(C,H);
		System.out.println("New Corrected Syndrome Matrix: ");
		// Prints out the matrix
		for(int i = 0; i < synd.length; i++)
		{
			for(int j = 0;j < synd[i].length; j++)
			{
				System.out.print(synd[i][j] + " " );
			}
			
			System.out.println();
		}
		
		// Calls the method that will create an array of binary words from a binary matrix, uses the name binArray variable
		binArray = parsedToString(C);
		
		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();
		
		// Converts the data into integer representation, then stores into a new array that will turn it into characters
		int[] binNumArray = new int[binArray.length];
		String str = "";
		for (int index = 0; index < binArray.length; index++)
		{
			binNumArray[index] = Integer.parseInt(binArray[index],2);
			str = str + (char) binNumArray[index];

		}
          
		
		System.out.println("The old password: " + string);
		System.out.println();
		System.out.println("The new correct password: " +str);
		System.out.println();
		System.out.println();


		/*************************************************************

			Start of new analysis

		**************************************************************/

		// Creates space between outputs
		System.out.println();
		System.out.println("*************************");
		System.out.println();
		System.out.println("\t    Scenario 2");
		System.out.println();
		System.out.println("*************************");
		System.out.println();
		System.out.println(); 
		
		// Gets the new syndrome matrix 
		int[][] syndTwo = binaryMatrixMult(C,H);
		errors = getErrors(H,synd);
		
		
 		// Creating a method that will correct the errors
		// @param: array of indicies, matrix of syndromes
		// @return matrix of corrected words
		D = allCorrectErrors(errors,C);
		System.out.println("Newly Corrected Word: ");
		for (int i = 0; i < D.length; i++)
		{
			for (int j = 0; j < D[i].length; j++)
			{
				System.out.print(D[i][j] + " ");
			}
			
			System.out.println();
		}
		
		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("New Corrected Syndrome Matrix: ");
		// Prints out the matrix
		for(int i = 0; i < syndTwo.length; i++)
		{
			for(int j = 0;j < syndTwo[i].length; j++)
			{
				System.out.print(syndTwo[i][j] + " " );
			}
			
			System.out.println();
		}

		// Calls the method that will create an array of binary words from a binary matrix, uses the name binArray variable
		binArray = parsedToString(D);

		// Creates space between outputs
		System.out.println();
		System.out.println();
		System.out.println();

		// Converts the data into integer representation, then stores into a new array that will turn it into characters
		int[] binNumArrayTwo = new int[binArray.length];
		String strTwo = "";
		for (int index = 0; index < binArray.length; index++)
		{
			binNumArrayTwo[index] = Integer.parseInt(binArray[index],2);
			strTwo = strTwo + (char) binNumArrayTwo[index];

		}


		System.out.println("The old password: " + string);
		System.out.println();
		System.out.println("The new correct password: " + strTwo);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();


		System.out.print("Enter the number of passwords you want to generate: ");
		numPasswords = keyboard.nextInt();

		// Creates space between inputs
		System.out.println();
		System.out.println();

		String[] passwords = getPasswordList(strTwo,synd,numPasswords);
		
		System.out.print("A List of generated passwords");
		System.out.println();
		System.out.println("------------------------------");
		for(int index = 0; index < passwords.length; index++)
		{
			System.out.println("  " + passwords[index]);
		}
	
		System.out.println();
		System.out.println();
		System.out.println();	

		
		

	}
	// End of Main


/**************************************************

 ~~ Start of method calls ~~

*****************************************************/	
	
	// Creating a method that will find the max length of each element in the array
	// @param: array of strings
	// @return: longest string
	public static int findMax(String[] A)
	{
		// List of local variables
		int max = A[0].length();
		
		// Creating an array to find the max length of a string in each index
		for (int index = 0; index < A.length; index++)
		{
			if (max < A[index].length())
			{
				max = A[index].length();
			}
		}
		
		return max;
	}
	// End
	
	// Creating a method that will convert all characters into the same length by adding zeros at the beginning.
	// NOTE: by adding zeros to the front of the string, does not change the ASCII for that character
	// @param: array of strings, longest string
	// @return: array of strings
	public static String[] getSameLength(String[] A, int max)
	{
		// List of local variables
		int diff;
		
		
		for (int index = 0; index < A.length; index++)
		{
			if (A[index].length() != max)
			{
				// Calls a function to determine the number of zeros needed to add to the string
				// @param: string array, the current index, the max length of string
				// @return: the differnce in length between the max string and the current string
				diff = getDifference(A,index,max);
				
				for (int jindex = 0; jindex < diff; jindex++)
				{
					A[index] = "0" + A[index];
				}
			}
		}
		
		return A;
	}
	// End
	
	// Creating a subroutine getDifference to find the number of missing characters
	// @param: array of strings, current index, longest string
	// @return: the different of characters between strings
	public static int getDifference(String[] A,int index, int max)
	{
		return max - A[index].length();
	}
	// End
	
	// Creating a method that will create a matrix from the array of binary words
	// @param: array of strings
	// @return: returns a binary matrix, where each row is an array of a binary representation of a character in password
	public static int[][] convertToMatrix(String[] A)
	{
		// Local variables
		char[][] B = new char[A.length][A[0].length()]; // NOTE: matrix the size of the word x length of the adjusted binary code
		int[][] C = new int[A.length][A[0].length()]; // Same dimensions as matrix above
		
		// Converts the array of strings into rows of characters
		for (int i = 0; i < B.length; i++)
		{
			for(int j = 0; j < B[i].length; j++)
			{
				char[] charArray = A[i].toCharArray();
				B[i][j] = charArray[j];
			}
		}
		
		// Parses the characters into integers
		for (int i = 0; i < B.length; i++)
		{
			for(int j = 0; j < B[i].length; j++)
			{
				String chr = Character.toString(B[i][j]);
				C[i][j] = Integer.parseInt(chr);
			}
		}
		
		return C;
	}
	// End

	// Creating a method that will perform matrix multiplication
	// @param: any integer matrix, any integer matrix
	// @return: a matrix that is a product of two matricies
	public static int[][] binaryMatrixMult(int[][]A,int[][]B)
	{
		// List of local variables
		int[][] C = new int[A.length][B[0].length];

		// Matrix Multiplication algorithm that convertst to binary
		for ( int i = 0; i < A.length; i++)
                {
                        for (int j = 0; j < B[0].length; j++)
                        {
                                for (int k = 0; k < A[0].length; k++)
                                {
                                        C[i][j] += A[i][k] * B[k][j];
                                }
							
				// Converts the values into binary
				C[i][j] = C[i][j] % 2;
                        }
                }

		return C;
	}
	// End


	// Creating a method that will check for the errors in the syndrome matrix and then cross reference them 
	// in the check-pairity matrix
	// @param: check-parity matrix, matrix of syndromes
	// @return: an array of indices where the errors of the syndrome are located with the check-parity matrix respectively
	public static int[] getErrors(int[][] H, int[][] S)
	{
		// List of local variables
		int[] E = new int[S.length];
		String[] A = new String[S.length];
		String[] B = new String[H.length];
		
		// Parses the matricies into strings to check errors
		// @param: matrix
		// @return: arrays of strings of each row of the matrix
		A = parsedToString(S);
		B = parsedToString(H);
		
		
		// Creating a loop that will check the values to find the errors
		// Iterates through the syndrome matrix and checks each element in the check-pairity matrix
		for (int i = 0; i < A.length; i++)
		{
			for (int j = 0; j < B.length; j++)
			{
				if (A[i].equals("000"))
				{
					E[i] = -1;
				}
				 else if (A[i].equals(B[j]))
				{
					E[i] = j;
					break;
				}
				else
				{
					E[i] = -2;
				}
			}
		}

		return E;
	}
	// End
		
	// Creating a method that will change the matrix into an a array of integers
	// i.e. the entire first row will be parsed into one digit
	// @param: any integer matrix
	// @return: converted parsed array of strings
	public static String[] parsedToString(int[][] M)
	{
		// List of local variables
		int[] A = new int[M.length];
		String[] C = new String[M.length];
		String str = "";
		String[][] B = new String[M.length][M[0].length];
		
		// Converts the array of individual matrix elements into parsed strings for comparision
		for (int i = 0; i < M.length; i++)
		{
			for (int j = 0; j < M[i].length; j++)
			{
				char c = Character.forDigit(M[i][j],10);
				str = str + c;
			}
			
			// Adds the string into the array
			C[i] = str;

			// Resets the string to null
			str = "";	
		}
		
		return C;
	}
	// End
	
	// Creating a method that will correct the errors
	// @param: Error array, Syndrome matrix
	// @return: Matrix of corrected errors
	public static int[][] correctErrors(int[] A, int[][] B)
	{
		// List of local variables
		int[][] C = new int[B.length][B[0].length];
		
		// Copies the matrix to return
		for (int i = 0; i < A.length; i++)
		{
			for (int j = 0; j < C[i].length; j++)
			{
				C[i][j] = B[i][j];
			}
		}
		

		for (int index = 0; index < C.length; index++)
		{
			if (A[index] == 0)
			{
				C[index][0] = (C[index][0] + 1)  % 2;
				
			}
			else if(A[index] == 1)
			{
				C[index][1] = (C[index][1] + 1) % 2;
			}
			else if(A[index] == 2)
			{
				C[index][2] = (C[index][2] + 1) % 2;
			}
			else if(A[index] == 3)
			{
				C[index][3] = (C[index][3] + 1) % 2;
			}
				
		}

		return C;
	}
	// End

	// Creating a method that will correct all errors in the syndrome matrix, including the ones that are linear combinations 
	// of the basis vectors in R^3
	// @param: error array, corrected errors
	// @return: matrix of corrected errors
	public static int[][] allCorrectErrors(int[] A, int[][]B)
	{
                // List of local variables
                int[][] C = new int[B.length][B[0].length];

                // Copies the matrix to return
                for (int i = 0; i < A.length; i++)
                {
                        for (int j = 0; j < C[i].length; j++)
                        {
                                C[i][j] = B[i][j];
                        }
                }


                for (int index = 0; index < C.length; index++)
                {
                        if (A[index] == 0)
                        {
                                C[index][0] = (C[index][0] + 1)  % 2;

                        }
                        else if(A[index] == 1)
                        {
                                C[index][1] = (C[index][1] + 1) % 2;
                        }
                        else if(A[index] == 2)
                        {
                                C[index][2] = (C[index][2] + 1) % 2;
                        }
                        else if(A[index] == 3)
                        {
                                C[index][3] = (C[index][3] + 1) % 2;
                        }
                        else if (A[index] == 4)
                        {
                                C[index][0] = (C[index][0] + 1)  % 2;

                        }
                        else if (A[index] == 5)
                        {
                                C[index][0] = (C[index][1] + 1)  % 2;

                        }
                        else if (A[index] == 6)
                        {
                                C[index][0] = (C[index][2] + 1)  % 2;

                        }
                }

                return C;
	}
	// End

	// Creatin a method that will generate a new set of passwords
	public static String[] getPasswordList(String passWord,int[][] synd,int numPass)
	{
		// List of lcoal variables in the numPass method
		String str = " \" !#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~';";
		String[] strArray = new String[numPass];
		String[] parsedArray = parsedToString(synd);
		char[] charArray = passWord.toCharArray();
		String newStr = "";

		Random randNum = new Random();


		// Creating loop that will generate a new list of passwords
		for (int index = 0; index < strArray.length; index++)
		{
			for (int value = 0; value < parsedArray.length; value++)
			{
				// Replaces the character to generate a new password
				if (parsedArray[value].equals("100"))
				{
					int randInt = randNum.nextInt(str.length());
					char val = str.charAt(randInt);
					charArray[value] = val;
				}
				else if (parsedArray[value].equals("010"))
				{
					int randInt = randNum.nextInt(str.length());
					char val = str.charAt(randInt);
					charArray[value] = val;
				}
				else if (parsedArray[value].equals("001"))
				{
					int randInt = randNum.nextInt(str.length());
					char val = str.charAt(randInt);
					charArray[value] = val;			
				}
				
				newStr = String.valueOf(charArray);
				strArray[index] = newStr;
			}
		}

		// Returns the new set of passwords
		return strArray;
	}
	// End

}
