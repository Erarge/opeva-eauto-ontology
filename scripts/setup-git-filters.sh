#!/bin/bash
# Setup script for git credential redaction filters (Linux/Mac)
# Run this script once to configure git filters on your local machine

echo "Setting up git filters for credential redaction..."

# Make the filter script executable
chmod +x scripts/git-filter-redact.sh

# Configure the clean filter (runs on commit)
git config filter.redact-credentials.clean 'scripts/git-filter-redact.sh'

# Configure the smudge filter (runs on checkout - we just pass through)
git config filter.redact-credentials.smudge 'cat'

echo "Git filters configured successfully!"
echo ""
echo "Test it by staging application.properties:"
echo "  git add src/main/resources/application.properties"
echo "  git diff --cached src/main/resources/application.properties"
echo ""
echo "You should see REDACTED values instead of real credentials."

