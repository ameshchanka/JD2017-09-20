FOR %%I In (*.txt) DO (

native2ascii -encoding utf-8 %%I ..\res\%%~nI.properties

)
