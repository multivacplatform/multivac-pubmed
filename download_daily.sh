##############################################################################
## Multivac PubMed ###########################################################
## This should be used everyday to download new updated articles from FTP   ##
##############################################################################
#!/usr/bin/env bash

cd data || return

wget -r -c -N -np -A "*.xml.gz" ftp://ftp.ncbi.nlm.nih.gov/pubmed/updatefiles/
