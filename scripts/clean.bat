@echo off
echo Cleaning up...
cd ..
REM Execute mvn clean on all submodules
for /d %%d in (*) do (
    if exist %%d\pom.xml (
        cd %%d
        mvn clean
        cd ..
    )
)
cd scripts
echo Done.