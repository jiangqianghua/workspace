
#this function uses the following environment variables
# TERRIER_OPTIONS
# TEST_NAME
function doShakeSpeare()
{
	#classical indexing
	TEST_NAME="$TEST_NAME with classical indexing" \
		INDEXING_OPTION=" -i " \
		doShakespeare_Root $*

	#classical indexing with merging
	TEST_NAME="$TEST_NAME with classical indexing and merging" \
		TERRIER_OPTIONS="$TERRIER_OPTIONS -Dindexing.max.docs.per.builder=12" \
		INDEXING_OPTION=" -i " \
		doShakespeare_Root $*

	#single pass indexing
	TEST_NAME="$TEST_NAME with single pass indexing" \
		INDEXING_OPTION=" -i -j " \
		doShakespeare_Root $*
	
	#single pass indexing with merging
	TEST_NAME="$TEST_NAME with single pass indexing and merging" \
		TERRIER_OPTIONS="$TERRIER_OPTIONS -Dindexing.max.docs.per.builder=12" \
		INDEXING_OPTION=" -i -j " \
		doShakespeare_Root $*
}

#INDEXING_OPTION
function doShakespeare_Root
{
	echo Running test ${TEST_NAME}
	mkdir -p var/test
    rm -rf var/test/*
    TERRIER_ETC=var/test bin/trec_setup.sh var/test/
    echo share/shakespeare-merchant.trec > var/test/collection.spec
    echo terrier.index.path=${PWD}/var/test >> var/test/terrier.properties
	echo ignore.low.idf.terms=false >> var/test/terrier.properties
    echo trec.results=${PWD}/var/test >> var/test/terrier.properties
    TERRIER_ETC=var/test bin/trec_terrier.sh $INDEXING_OPTION
	echo =========================
	echo Index properties
	echo =========================
	cat var/test/data.properties
	#cat var/test/data_*.properties
	ls var/test/
	echo =========================
	for i in $*;
	do
		echo $i >> var/test/trec.topics.list
	done
	NUMQ=`cat var/test/trec.topics.list | grep -v '^#' | xargs cat |grep -v '^#' | wc -l `
    echo ${PWD}/share/tests/test.shakespeare-merchant.all.qrels > var/test/trec.qrels
    echo trec.topics.parser=SingleLineTRECQuery >> var/test/terrier.properties
	#TERRIER_ETC=var/test bin/trec_terrier.sh --printlexicon | sed 's/^/LEX: /'
    TERRIER_ETC=var/test bin/trec_terrier.sh -r
    TERRIER_ETC=var/test bin/trec_terrier.sh -e
	cat var/test/*.eval
	MAP=`cat var/test/*.eval | grep "Average Precision"| sed "s/Average Precision: //" | head -1`
	RUNQ=`cat var/test/*.eval | grep "Number of queries" |  sed "s/Number of queries  = //" | head -1`
	if [ -z "$RUNQ" ];
	then
		RUNQ=0
	fi
	RES=`cat var/test/*.res`
	rm -rf var/test
	if [ $RUNQ  -eq $NUMQ ];
	then
		if [ "$MAP" == "1.0000" ];
		then
    		echo "****************"; 
			echo Test ${TEST_NAME} passed: MAP Good; 
			echo "****************"; 
			return 0;
		else 
			echo "****************"; 
			echo Test ${TEST_NAME} failed: Bad MAP $MAP; 
			echo "****************"; 
			echo $RES
			return 1; 
		fi
	else
		echo "****************";
		echo Test ${TEST_NAME} failed: not enough queries: $RUNQ found, $NUMQ expected. MAP was $MAP;
		echo "****************";
		echo $RES	
		return 2;	
	fi

}
