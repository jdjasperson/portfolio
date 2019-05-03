source /Users/jerryjasperson/.basheditor/remote-debugging-v1.sh localhost 33333 #BASHEDITOR-TMP-REMOTE-DEBUGGING-END
#!/bin/bash
mvn -Dtest=portfolio.application.boggle.BoggleTest -Dmaven.surefire.debug -DdebugForkedProcess=true test
