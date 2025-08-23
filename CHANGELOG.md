# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added

- `ApiKeyStore` class using `EncryptedSharedPreferences` for secure API key storage.
- Added `androidx.security:security-crypto` dependency.

### Removed

- API key preferences (`API_KEY_FMP`, `API_KEY_AV`), flows and save methods from `DataStore`.

### Changed

- `DataStore` no longer handles API keys; use `ApiKeyStore` for all API key operations.