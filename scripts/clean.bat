@echo off
echo Cleaning up...
cd ..
for /d %%d in (*) do (
    if exist %%d\pom.xml (
        cd %%d
        mvn clean
        cd ..
    )
)
cd scripts
echo Done.