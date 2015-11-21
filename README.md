# csci3170
workflow:
=====
1. git pull
2. write code!
3. git add \<FILES\>
4. git commit -m "\<MESSAGE\>"
5. do step 3&4 whenever you have finished adding a new feature 
6. git push origin master
7. if there are merge conflicts, git pull
8. fix conflicts and redo from step3

Git diff
====

- working directory vs last added/committed changes (unstaged changes)
	git diff -- \<FILENAME\>
- staged vs a commit
	git diff --cached \<COMMIT ID\> -- \<FILENAME\> 
	if \<COMMIT ID\> is empty, the latest checkout-ed commit (HEAD) is used
- old commit vs new commit
	git diff \<COMMIT ID\> \<COMMIT ID\> -- \<FILENAME\>

- if \<FILENAME\> is not provided, all changed files will be shown
- double dashes -- means end of cmd line flags
