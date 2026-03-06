$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot"
$env:M2_HOME = "d:\SampleSns\maven\apache-maven-3.9.12"
$env:PATH = "$env:JAVA_HOME\bin;$env:M2_HOME\bin;$env:PATH"

Write-Output "Using JAVA_HOME: $env:JAVA_HOME"
Write-Output "Using Maven from: $env:M2_HOME"

cd d:\SampleSns\backend
mvn test
