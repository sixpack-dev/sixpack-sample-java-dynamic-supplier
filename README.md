How to get started with this project:

1. Signup at https://app.sixpack.dev/
2. Go to [settings](https://app.sixpack.dev/account/config) and download private key and certificate to `./config`
3. Update `pom.xml` and set `sixpack.sdk.version` to your published/internal Java SDK version.
4. Set the following environment variables:
   - `SIXPACK_ACCOUNT` - your account name (available in the [settings](https://app.sixpack.dev/account/config))
   - `SIXPACK_AUTH_TOKEN` - token from the [settings](https://app.sixpack.dev/account/config) page
   - `SIXPACK_CLIENT_CERT_PATH=config/generator.pem` - path to the certificate
   - `SIXPACK_CLIENT_KEY_PATH=config/generator.key` - path to the private key
   - `SIXPACK_ENVIRONMENT` - environment label in Sixpack
   - `SIXPACK_URL=gen.sixpack.dev:443` - URL of the Sixpack server
5. Run `MySupplier`.

What this sample demonstrates:

- Dynamic item discovery via `DynamicSupplier#discoverItems`.
- Runtime manifest refresh (baseline manifest first, enhanced manifest after refresh).
- Handling dynamic generation calls by item name in `DynamicSupplier#generate`.
