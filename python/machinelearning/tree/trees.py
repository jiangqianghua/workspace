from math import log
import operator
import copy
import pickle
def createDataSet():
	dataSet = [[1,1,'yes'],[1,1,'yes'],[1,0,'no'],[0,1,'no'],[0,0,'no']]
	labels = ['no surfacing' , 'flippers']
	return dataSet , labels

def calcShannonEnt(dataSet):
	numEntries = len(dataSet) ;
	labelCounts = {}
	for featVec in dataSet:
		currentLabel = featVec[-1]
		labelCounts[currentLabel] = labelCounts.get(currentLabel,0) + 1
	shannonEnt = 0.0

	for key in labelCounts:
		prob = float(labelCounts[key])/numEntries
		shannonEnt -= prob *log(prob,2)
	return shannonEnt
def splitDataSet(dataSet , axis ,value):
	retDataSet = [] 
	for featVec in dataSet:
		if featVec[axis] == value:
			reducedFeatVec = featVec[:axis]
			reducedFeatVec.extend(featVec[axis+1:])
			retDataSet.append(reducedFeatVec)
	return retDataSet

def chooseBestFeatureToSplit(dataSet):
	numFeature = len(dataSet[0]) - 1
	baseEntropy = calcShannonEnt(dataSet)
	bestInfoGain = 0.0 
	bestFeature = -1

	for i in range(numFeature):
		featList = [example[i] for example in dataSet]
		uniqueVals = set(featList)
		newEntropy = 0.0
		for value in uniqueVals:
			subDataSet = splitDataSet(dataSet , i ,value)
			prob = len(subDataSet)/float(len(dataSet))
			newEntropy += prob * calcShannonEnt(subDataSet)
		infoGain = baseEntropy - newEntropy
		if infoGain > bestInfoGain:
			bestInfoGain = infoGain
			bestFeature = i
	return bestFeature

def majorityCnt(classList):
	classCount = {}
	for vote in classList:
		if vote not in classCount.keys(): classCount[vote] = 0
		classCount[vote] += 1
	sortedClassCount = sorted(classCount.iteritems(),key = operator.itemgetter(1),reverse = True)
	return sortedClassCount[0][0]

def createTree(dataSet , labels):
	classList = [example[-1] for example in dataSet]
	if classList.count(classList[0]) == len(classList):
		return classList[0]
	if len(dataSet[0]) == 1:
		return majorityCnt(classList)
	bestFeat = chooseBestFeatureToSplit(dataSet)
	bestFeatLabel = labels[bestFeat]
	myTree = {bestFeatLabel:{}}
	del(labels[bestFeat])
	featValues = [e[bestFeat] for e in dataSet]
	uniqueVals = set(featValues)
	for value in uniqueVals :
		subLabels = labels[:]
		myTree[bestFeatLabel][value] = createTree(splitDataSet(dataSet,bestFeat ,value),subLabels)
	return myTree

def classify(inputTree , featLabels , testVec):
	firstStr = inputTree.keys()[0]
	secondDict = inputTree[firstStr]
	featIndex = featLabels.index(firstStr)
	for key in secondDict.keys():
		if testVec[featIndex] == key:
			if type(secondDict[key]).__name__ == 'dict':
				classLabel = classify(secondDict[key],featLabels ,testVec)
			else:
				classLabel = secondDict[key]
	return classLabel

def storeTree(inputTree , filename):
	fw = open(filename,'w')
	pickle.dump(inputTree , fw)
	fw.close()

def grabTree(filename):
	fr = open(filename)
	return pickle.load(fr)

if __name__ == '__main__':
	myDat,labels = createDataSet()
	print myDat
	print labels
	storeTree(myDat,'myTree')
	loadDat = grabTree('myTree')
	print ' get data from file:',loadDat
	featLabels = copy.copy(labels)
#	print calcShannonEnt(myDat)
	
	print '------------'

#	print splitDataSet(myDat , 1 , 1)

#	print '--------------'

#	featList = [example[1] for example in myDat]
#	print set(featList)

	print '----getbeastFeature-------'
#	bestFeature = chooseBestFeatureToSplit(myDat)
#	print bestFeature
#	print '---majorityCnt----'
#	classList = [example[-1] for exmaple in myDat]
#	print majorityCnt(classList)
	
#	print [e[0] for e in myDat]	
	print '--- creatTree---'
#	print createTree(myDat , labels)
	myTree = createTree(myDat, labels)
	print classify(myTree , featLabels, [1,1])
	print classify(myTree , featLabels,[1,0])
