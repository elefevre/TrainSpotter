#!/bin/bash

PROJECT_DIR=$(pwd)
BRANCH=$(git branch --no-color | awk '$1=="*" {print $2}')
ORIGIN=$(git remote -v | awk '$1=="origin" && $3=="(push)" {print $2}')

git fetch
git add -A; git ls-files --deleted -z | xargs -0 -I {} git rm {}; git commit -m "wip"
git rebase origin/${BRANCH}

if [ "$?" -ne 0 ]
then
	git rebase --abort
	git log -n 1 | grep -q -c "wip" && git reset HEAD~1
	echo "Unable to rebase. please pull or rebase and fix conflicts manually."
fi
git log -n 1 | grep -q -c "wip" && git reset HEAD~1

rm -Rf ../privatebuild_trainspotter
git clone -slb "${BRANCH}" . ../privatebuild_trainspotter
cd ../privatebuild_trainspotter/trainspotter

play dependencies --sync
play auto-test

if [ "$1" == "no-push" ]; then
	echo "Not publishing, as per the no-push option"; exit 0
fi

if [ -f test-result/result.passed ]; then
	git push $ORIGIN $BRANCH
	if [ $? -ne 0 ]; then
		echo "Unable to push"
	else
		echo "Yet another successful push!"
	fi
else
	echo "Unable to build"
fi

cd ${PROJECT_DIR} && git fetch
