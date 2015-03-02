from numpy import *
import operator

def loadDataSet():
	dataMat = []
	labelMat = []
	fr = open('testSet.txt')
	for line in fr.readlines():
		lineArr = line.strip().split()
		dataMat.append([1.0,float(lineArr[0]),float(lineArr[1])])
		labelMat.append(int(lineArr[2]))
	return dataMat , labelMat

def sigmoid(inX):
	return 1.0/(1+exp(-inX))

def gradAscent(dataMatIn , classLabels):
	dataMatrix = mat(dataMatIn)
	labelMat = mat(classLabels).transpose()
	m , n = shape(dataMatrix)
	alpha = 0.001 
	maxCycles = 500
	weights = ones((n,1))
	for k in range(maxCycles):
		h = sigmoid(dataMatrix*weights)
		error = (labelMat - h)
		weights = weights + alpha * dataMatrix.transpose()*error
	return weights

def stocGradAscent0(dataMatrix, classLabels):
	m , n = shape(dataMatrix)
	alpha = 0.01 
	weights = ones(n)
	for i in range(m):
		h = sigmoid(sum(dataMatrix[i]*weights))
		error = classLabels[i] - h
		weights = weights + alpha * error * dataMatrix[i]
	return weights
if __name__ == '__main__':
	dataArr , labelMat = loadDataSet()
#	print dataArr , labelMat
#	weights = gradAscent(dataArr , labelMat)
	weights = stocGradAscent0(array(dataArr),labelMat)
	print weights
