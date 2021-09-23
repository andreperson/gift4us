SET mypath=%~dp0
mvn -f  %mypath:~0,-1% clean eclipse:eclipse -Dwtpversion=2.0 -DAMBIENTE=desenvolvimento
