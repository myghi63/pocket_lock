# PocketTranslations

Static RRO for the five `pocket_mode_*` strings that the patch chain adds to
`frameworks/base/core/res` (framework package `android`). Keeping them out of
the patch keeps the patch minimal and lets locales be refreshed independently.

Provenance of the text:

- AOSPA (author of the pocket lock patch) ships these strings English-only.
- 28 locales come verbatim from crDroid's Crowdin translations, taken from
  `core/res/res/values-<locale>/cr_strings.xml` of
  [crdroidandroid/android_frameworks_base](https://github.com/crdroidandroid/android_frameworks_base).
- 10 locales (`ca`, `fi`, `hr`, `sr`, `sv`, `nb`, `da`, `hi`, `ms`, `th`) have no
  upstream translation; the text here is best-effort and needs native review.

Note: crDroid publishes the Crowdin text under region qualifiers
(`values-es-rES`, not `values-es`), so a device using the base locale without a
region (or another region) falls back to English, same as upstream.

To refresh from upstream:

```sh
for l in $(gh search code --owner crdroidandroid "pocket_mode_instruction_step2" \
        --json path --jq '.[].path' | sed -n 's|core/res/res/values-\(.*\)/cr_strings.xml|\1|p'); do
    curl -fsSL -o "$l.xml" \
      "https://raw.githubusercontent.com/crdroidandroid/android_frameworks_base/HEAD/core/res/res/values-$l/cr_strings.xml"
done
```
