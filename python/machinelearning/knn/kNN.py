from numpy import *
import operator
import os
def createDataSet():
	group = array([[1.0,1.1],[1.0,1.0],[0,0],[0,0.1]])
	labels = ['A','A','B','B']
	return group , labels


def classify0(inX , dataSet , labels , k):
	dataSetSize = dataSet.shape[0]
	diffMat = tile(inX , (dataSetSize , 1)) - dataSet
	sqDiffMat = diffMat**2
	sqDistances = sqDiffMat.sum(axis=1)
	distances = sqDistances**0.5
	sortedDistIndicies = distances.argsort()
	classCount = {}
	for i in range(k):
		voteIlabel = labels[sortedDistIndicies[i]]
		classCount[voteIlabel] = classCount.get(voteIlabel,0) + 1
	sortedClassCount = sorted(classCount.iteritems(),key=operator.itemgetter(1),reverse=True)
	return sortedClassCount[0][0]


def file2matrix(filename):
	fr = open(filename)
	arrayOLines = fr.readlines()
	numberOfLines = len(arrayOLines)
	returnMat = zeros((numberOfLines,3))
	classLabelVector = [] 
	index = 0
	for line in arrayOLines:
		line = line.strip()
		listFromLine = line.split('\t')
		returnMat[index,:] = listFromLine[0:3]
		classLabelVector.append(int(listFromLine[-1]))
		index = index + 1
	return returnMat , classLabelVector

def qutoNorm(dataSet):
	minVals = dataSet.min(0)
	maxVals = dataSet.max(0)
	ranges = maxVals - minVals
	m = dataSet.shape[0]
	normalDataSet = dataSet - tile(minVals , (m ,1))
	normalDataSet = normalDataSet/tile(ranges , (m ,1))
	return normalDataSet , ranges , minVals
def datingClassTest():
	hoRatio = 0.10
	datingDataMat,datingLabels = file2matrix('datingTestSet2.txt')
	normMat , ranges , minVals = qutoNorm(datingDataMat)
	m = normMat.shape[0]
	numTestVecs = int(m*hoRatio)
	errorCount = 0.0
	for i in range(numTestVecs):
		classifierResult = classify0(normMat[i,:] , normMat[numTestVecs:m,:],datingLabels[numTestVecs:m],3)
		print "the classifier came back with %d , the real answer is: %d"%(classifierResult,datingLabels[i])
		if(classifierResult != datingLabels[i]): errorCount = errorCount + 1.0
	print "the total error rate is: %f"%(errorCount/float(numTestVecs))
	print "%f  %f" %(errorCount , float(numTestVecs))

def img2vector(filename):
	returnVect = zeros((1,1024))
	fr = open(filename)
	for i in range(32):
		lineStr = fr.readline()
		for j in range(32):
			returnVect[0,32*i+j] = int(lineStr[j])
	return returnVect

def handwritingClassTest():
	hwLabels = []
	trainingFileList = os.listdir('./digits/trainingDigits')
	m = len(trainingFileList)
	trainingMat = zeros((m,1024))
	for i in range(m):
		fileNameStr = trainingFileList[i]
		fileStr = fileNameStr.split('.')[0]
		classNameStr = int(fileStr.split('_')[0])
		hwLabels.append(classNameStr)
		trainingMat[i,:] = img2vector('./digits/trainingDigits/%s'%fileNameStr)
	testFileList = os.listdir('./digits/testDigits')	
	mTest = len(testFileList)
	testMat = zeros((mTest,1024))
	errorCount = 0.0
	for i in range(mTest):
		fileNameStr = testFileList[i]
		fileStr = fileNameStr.split('.')[0]
		classNameStr = int(fileStr.split('_')[0])
		vectorUnderTest = img2vector('./digits/testDigits/%s'%fileNameStr)
		classifierResult = classify0(vectorUnderTest , trainingMat , hwLabels , 10)
#		print 'the classifier came back with: %d, the real answer is: %d'%(classifierResult , classNameStr)
		if(classifierResult != classNameStr):errorCount = errorCount + 1
	print 'the total number of errors is: %d'%errorCount
	print 'the total number of test is: %d'%mTest
	print 'the total error rate is: %f'%(errorCount/float(mTest))
		
	
if __name__ == '__main__':
	
#	testVect = img2vector('./digits/testDigits/0_0.txt')
#	print testVect[0,0:32]
	handwritingClassTest()
