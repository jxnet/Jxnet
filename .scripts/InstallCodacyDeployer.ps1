
#---------------------------------#
#      general configuration      #
#---------------------------------#

if (Test-Path "C:\tools\codacy.jar") {
    echo "Codacy is already installed"
} else {
    $urlPath = "https://github.com/codacy/codacy-coverage-reporter/releases/download/4.0.2/codacy-coverage-reporter-4.0.2-assembly.jar"

    # Download the file
    echo "Downloading... ($urlPath)"
    wget $urlPath -UseBasicParsing -OutFile "C:\tools\codacy.jar"

    echo "Installing Codacy..."
    echo "Codacy has been installed."
}
