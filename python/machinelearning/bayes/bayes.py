from  numpy import *
import math
def loadDataSet():
	postingList = [['my','dog','has','flea','problems','help','please'],['maybe','not','take','him','to','dog','park','stupid'],['my','dalmation','is','so','cute','I','love','him'],['stop','posting','stupid','worthless','garbage'],['mr','licks','ate','my','steak','how','to','stop','him'],['quit','buying','worthless','dog','food','stupid']]
	classVec = [0 , 1 , 0 , 1 ,0 ,1]
	return postingList,classVec

def createVocabList(dataSet):
	vocabSet = set([])
	for document in dataSet:
		vocabSet = vocabSet | set(document)
	return list(vocabSet)

def setOfWords2Vec(vocabList , inputSet):
	returnVec = [0]*len(vocabList)
	for word in inputSet:
		if word in vocabList:
			returnVec[vocabList.index(word)] = 1
	return returnVec

def trainNB0(trainMatrix , trainCategory):
	numTrainDocs = len(trainMatrix) 
	numWords = len(trainMatrix[0])
	pAbusive = sum(trainCategory) / float(numTrainDocs)
	p0Num = ones(numWords)
	p1Num = ones(numWords)
	p0Denom = 2.0
	p1Denom = 2.0
	for i in range(numTrainDocs):
		if trainCategory[i] == 1:
			p1Num = p1Num + trainMatrix[i]
			p1Denom = p1Denom + sum(trainMatrix[i])
		else:
			p0Num = p0Num + trainMatrix[i]
			p0Denom = p0Denom + sum(trainMatrix[i])
	p1Vect = log(p1Num/p1Denom)
	p0Vect = log(p0Num/p0Denom)

	return p0Vect , p1Vect ,pAbusive
def classifyNB(vec2Classify , p0Vec , p1Vec ,pClass1):
	p1 = sum(vec2Classify*p1Vec) + log(pClass1)
	p0 = sum(vec2Classify*p0Vec) + log(1.0 - pClass1)
	if p1 > p0:
		return 1
	else:
		return 0

if __name__ == '__main__':
	listOPosts , listClasses = loadDataSet()
	myVocabList = createVocabList(listOPosts)
	print myVocabList
	#vecs = setOfWords2Vec(myVocabList , listOPosts[0])
	#print vecs
	trainMat = []
	for postionDoc in listOPosts:
		trainMat.append(setOfWords2Vec(myVocabList,postionDoc))
	p0V , p1V ,pAb = trainNB0(trainMat , listClasses)
	#print p0V
	#print p1V
	#print pAb
#	testEntry = ['love','my','dalmation']
	testEntry = ['stupid','garbage']
	thisDoc = array(setOfWords2Vec(myVocabList,testEntry))
	print testEntry , ' classified ad:' , classifyNB(thisDoc , p0V , p1V ,pAb)
