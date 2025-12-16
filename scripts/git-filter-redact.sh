#!/bin/bash
# Git clean filter to redact credentials from application.properties on commit
sed -E 's/^(connection\.(ip|username|password)=).*/\1REDACTED/g'

