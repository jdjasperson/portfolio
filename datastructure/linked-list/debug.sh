#!/bin/bash
mvn -Dtest=portfolio.datastructure.linkedlist.LinkedListTest -Dmaven.surefire.debug -DdebugForkedProcess=true test
