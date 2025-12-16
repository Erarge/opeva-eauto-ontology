# Setup script for git credential redaction filters (Windows PowerShell)
# Run this script once to configure git filters on your local machine

Write-Host "Setting up git filters for credential redaction..." -ForegroundColor Green

# Get the repository root (where .git directory is located)
$repoRoot = git rev-parse --show-toplevel
$scriptPath = Join-Path $repoRoot "scripts\git-filter-redact.ps1"

# Configure the clean filter (runs on commit) - use absolute path
git config filter.redact-credentials.clean "powershell -File `"$scriptPath`""

# Configure the smudge filter (runs on checkout - we just pass through)
git config filter.redact-credentials.smudge 'cat'

Write-Host "Git filters configured successfully!" -ForegroundColor Green
Write-Host ""
Write-Host "Repository root: $repoRoot" -ForegroundColor Cyan
Write-Host "Filter script: $scriptPath" -ForegroundColor Cyan
Write-Host ""
Write-Host "Test it by staging application.properties:" -ForegroundColor Yellow
Write-Host "  git add src/main/resources/application.properties" -ForegroundColor Yellow
Write-Host "  git diff --cached src/main/resources/application.properties" -ForegroundColor Yellow
Write-Host ""
Write-Host "You should see REDACTED values instead of real credentials." -ForegroundColor Yellow
