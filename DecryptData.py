## Creating an implementation of the DecryptData.java in python

import random


#### List of functions ####


## Creating a function that will convert a string into a binary matrix with each row its respective binary word
## @param: string of length 13
## @return: binary matrix of the strings
def convertToBinaryMatrix(s):
	A = []
	A = map(bin,bytearray(s))
	
	## Only keeps the rest of the string without the 0b portion
	for index in range(len(A)):
		A[index] = A[index][2:]

	## Calls a function that will find the max length of characters
	maxLength = findMax(A)

	## Converts the array of binary words into a matrix	
	B = []
	for i in range(len(A)):
		B.append([])
		for j in range(maxLength):
			if len(A[i]) != maxLength:
				A[i] = '0' + A[i]
				B[i].append(int(A[i][j]))
			else:
				B[i].append(int(A[i][j]))
	return B	
## End





## Creating a function that will determine the maximum length of a string
## @param: an array of binary strings
## @return: maximum length of the binary strings
def findMax(A):
	max = len(A[0])
	
	for index in range(len(A)):
		if len(A[index]) > max:
			max = len(A[index])
	return max
## End






## Creating a function that will perform matrix multiplication
## @param: any two matricies where A is n x m and B is m x p
## @return: a new matrix of size n x p
def matrixMult(A,B):
	C = []
	
	## First need to initilize the C matrix with zeros
	for i in range(len(A)):
		C.append([])
		for j in range(len(B[0])):
			C[i].append(0)

	## Performs the matrix multiplication	
	for i in range(len(A)):	
		for j in range(len(B[0])):			
			for k in range(len(B)):
				C[i][j] +=  A[i][k] * B[k][j]
			C[i][j] = (C[i][j]) % 2	
	
	return C
## End








## Creating a function that will get the errors indices of the errors
## @param: syndrome matrix and the check-parity matrix
## @return: an array of indices where the errors are in the check parity matrix
def getErrors(S,H):
	
	## Initializes the array of errors
	E = [ 0 ] * len(S)
	
	## Parses the binary matrices into a binary word array
	syndArray = convertToStrArray(S)
	parityArray = convertToStrArray(H)
	
	
	for i in range(len(syndArray)):
		for j in range(len(parityArray)):
			## Checks for no error, no errors get -1
			if syndArray[i] == "000":
				E[i] = -1
			## Checks for an error in the array, gives the index in which it is located
			elif syndArray[i] == parityArray[j]:
				E[i] = j
				break
			## Gives a -2 for an element not being in the array
			else:
				E[i] = -2
	return E

## End					



## Creating a function that will convert an array a binary matrix into an array of strings
## @param: binary matrix
## @return: string array
def convertToStrArray(M):
	N = []
	s = ""
	for i in range(len(M)):
		c = M[i]
		for j in range(len(M[i])):
			s = s + str(c[j])
		N.append(s)
		s = ""
	return N
## End	



## Creating a fucntion that will correct the errors
## @param: error array and binary matrix
## @return: correct binary matrix
def correctErrors(E,W):
	C = W
	
	for index in range(len(C)):
		if E[index] == 0:
			C[index][0] = (C[index][0] + 1) % 2
		elif E[index] == 1:
			C[index][1] = (C[index][1] + 1) % 2
		elif E[index] == 2:
			C[index][2] = (C[index][2] + 1) % 2
		elif E[index] == 3:
			C[index][3] = (C[index][3] + 1) % 2

	return C
## End



## Creating a function that will generate a list of passwords based on the corrected word
## @param: password string, number of passwords desired, array of syndromes
## @return: list of generated passwords
def generatePasswords(s,n,S):
	str = "' \"!#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~';"
	passwordList = []
	passwordSet = set()
	syndArray = convertToStrArray(S)
	origPass = s
	## Adds the password to the list
	passwordList.append(s)
	
	## Creating a loop that will generate the new passwords
	for index in range(n):
		for index in range(0,13):
			if syndArray[index] == "100":
				s = s.replace(s[index], random.choice(str))
			elif  syndArray[index] == "010":
				s = s.replace(s[index], random.choice(str))
			elif syndArray[index] == "001":
				s = s.replace(s[index], random.choice(str))
		passwordSet.add(s)
		s = origPass

	passwordList = list(passwordSet)

	return passwordList

## End

## Creating a function that will convert the correct word into a string
## @param: binary matrix
## @return: new string
def parsedToString(W):
	wordArray = convertToStrArray(W)
	s = ""

	for index in range(len(wordArray)):
		s = s + str(chr(int(wordArray[index],2)))
	return s	
## End




#### End of list of functions ####



#########################################

## Main ##

## List of global varialbes
H = [[1,1,0],[0,1,1],[1,1,1],[1,0,1],[1,0,0],[0,1,0],[0,0,1]]

## Creates the binary matrix
oldPassword = "fl38Cd%mr.ypJ"
W = convertToBinaryMatrix(oldPassword)

S = matrixMult(W,H)

print
print "W"
print "--------------"
for i in range(len(W)):
	for j in range(len(W[i])):
		print W[i][j],
	print

## Creates space between outputs
print
print

print "H"
print "-----"
for i in range(len(H)):
	for j in range(len(H[i])):
		print H[i][j],
	print

## Creates space between outputs
print
print


print "S"
print "-----"
## prints the matrix
for i in range(len(S)):
	for j in range(len(S[i])):
		print S[i][j],
	print

## Creates space between outputs
print
print

E = getErrors(S,H)

print "E = ", E

## Creates space between outputs
print
print
print


N = correctErrors(E,W)

print "W'"
print "--------------"
for i in range(len(N)):
	for j in range(len(N[i])):
		print N[i][j],
	print

## Creates space between output
print
print

newPassword = parsedToString(N)


print "The old password: ",oldPassword
print "The new corrected password: ", newPassword

## Creates space between inputs
print
print
print



## Calculates the new corrected sydrome matrix
newSyndrome = matrixMult(N,H)


print newSyndrome
print
print
print

## Gets the user to enter a an integer number that will generate a new set of passwords
n = int(raw_input("Enter an integer value to set a range of passwords to generate: "))

while n < 0:
	print "Error!! Please enter a positive integer value."
	print
	n = int(raw_intput("Enter a POSITIVE integer value as a range to generate passwords: "))


passwordList = generatePasswords(newPassword,n,newSyndrome)

print
print
print "List of generated passwords"
print "----------------------------"
for index in passwordList:
	print index

## Creates space between inputs
print
print

