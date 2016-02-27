#!/bin/bash

. share/tests/test.lib.sh


TEST_NAME="Basic" doShakeSpeare \
	share/tests/test.shakespeare-merchant.basic.topics

TEST_NAME="Basic UTF" TERRIER_OPTIONS="-Dstring.use_utf=true" doShakeSpeare \
	share/tests/test.shakespeare-merchant.basic.topics

TEST_NAME="Basic+Fields" TERRIER_OPTIONS="-DFieldTags.process=TITLE,SPEAKER" doShakeSpeare \
	share/tests/test.shakespeare-merchant.basic.topics \
	share/tests/test.shakespeare-merchant.field.topics

TEST_NAME="Basic+Fields UTF" TERRIER_OPTIONS="-Dstring.use_utf=true -DFieldTags.process=TITLE,SPEAKER" doShakeSpeare \
    share/tests/test.shakespeare-merchant.basic.topics \
    share/tests/test.shakespeare-merchant.field.topics

TEST_NAME="Basic with UTF Collection"  TERRIER_OPTIONS="-Dtrec.collection.class=TRECUTFCollection -Dstring.use_utf=true" doShakeSpeare \
    share/tests/test.shakespeare-merchant.basic.topics
