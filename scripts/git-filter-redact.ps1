# Git clean filter to redact credentials from application.properties on commit (PowerShell)
# This script reads from stdin and outputs the redacted content

$reader = [System.Console]::In
$writer = [System.Console]::Out

while ($null -ne ($line = $reader.ReadLine())) {
    if ($line -match '^(connection\.(ip|username|password)=)(.*)$') {
        $writer.WriteLine($matches[1] + 'REDACTED')
    } else {
        $writer.WriteLine($line)
    }
}
