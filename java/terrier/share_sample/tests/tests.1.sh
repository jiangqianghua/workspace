#!/bin/bash

. share/tests/test.lib.sh

#The following topic set is disabled, as Terrier's index format cannot support such queries accurately
#share/tests/test.shakespeare-merchant.phrase-fields.topics

TEST_NAME="Blocks" TERRIER_OPTIONS="-Dblock.indexing=true" doShakeSpeare \
	share/tests/test.shakespeare-merchant.basic.topics \
	share/tests/test.shakespeare-merchant.phrase.topics

TEST_NAME="Blocks UTF" TERRIER_OPTIONS="-Dblock.indexing=true -Dstring.use_utf=true" doShakeSpeare \
    share/tests/test.shakespeare-merchant.basic.topics \
    share/tests/test.shakespeare-merchant.phrase.topics

TEST_NAME="Blocks+fields" TERRIER_OPTIONS="-DFieldTags.process=TITLE -Dblock.indexing=true" doShakeSpeare \
	share/tests/test.shakespeare-merchant.basic.topics \
	share/tests/test.shakespeare-merchant.field.topics \
	share/tests/test.shakespeare-merchant.phrase.topics 

TEST_NAME="Blocks+fields UTF" TERRIER_OPTIONS="-DFieldTags.process=TITLE -Dblock.indexing=true -Dstring.use_utf=true" doShakeSpeare \
    share/tests/test.shakespeare-merchant.basic.topics \
    share/tests/test.shakespeare-merchant.field.topics \
    share/tests/test.shakespeare-merchant.phrase.topics
