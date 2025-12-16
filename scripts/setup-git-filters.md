# Git Filter Setup Instructions

This repository uses git filters to automatically censor credentials in `application.properties` when committing to git.

## Quick Setup

### Windows PowerShell
```powershell
.\scripts\setup-git-filters.ps1
```

### Linux/Mac
```bash
chmod +x scripts/setup-git-filters.sh
./scripts/setup-git-filters.sh
```

## Manual Setup (Windows PowerShell)

1. Configure git filter:
   ```powershell
   git config filter.redact-credentials.clean 'powershell -f "$PWD/scripts/git-filter-redact.ps1"'
   git config filter.redact-credentials.smudge 'cat'
   ```

## Manual Setup (Linux/Mac)

1. Make the script executable:
   ```bash
   chmod +x scripts/git-filter-redact.sh
   ```

2. Configure git filter:
   ```bash
   git config filter.redact-credentials.clean 'scripts/git-filter-redact.sh'
   git config filter.redact-credentials.smudge 'cat'
   ```

## How it works

- **On commit (clean)**: Credentials in `connection.ip`, `connection.username`, and `connection.password` are automatically replaced with `REDACTED`
- **On checkout (smudge)**: The file remains unchanged (your local file keeps real values)

## Testing

After setup, you can test it:
```bash
# Check what will be committed
git add src/main/resources/application.properties
git diff --cached src/main/resources/application.properties
```

You should see `REDACTED` values instead of real credentials.

