
#---------------------------------#
#      general configuration      #
#---------------------------------#

if (Test-Path "C:\tools\mingw64\bin") {
    echo "Mingw64 is already installed"
} else {
    $urlPath = "https://excellmedia.dl.sourceforge.net/project/mingw-w64/Toolchains targetting Win64/Personal Builds/mingw-builds/8.1.0/threads-posix/sjlj/x86_64-8.1.0-release-posix-sjlj-rt_v6-rev0.7z"

    # Download the file
    echo "Downloading... ($urlPath)"
    wget $urlPath -UseBasicParsing -OutFile $PSScriptRoot"\mingw64.7z"

    echo "Installing Mingw64..."
    Get-ChildItem $PSScriptRoot"\mingw64.7z" | % {& "C:\Program Files\7-Zip\7z.exe" "x" $_.FullName "-oC:\tools\"}

    echo "Mingw64 has been installed."
}
