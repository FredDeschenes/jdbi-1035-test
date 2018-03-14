Test case for [this](https://github.com/jdbi/jdbi/issues/1035) JDBI issue.

To run:
```shell
$ ./gradlew[.bat] run
```

To get the error, comment the `KotlinSqlObjectPlugin` installation in [Main.kt](src/main/kotlin/Main.kt) and re-run.