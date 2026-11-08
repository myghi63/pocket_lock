# Sourced by build/envsetup.sh. Patches become commits on the project's detached
# HEAD, which the next repo sync discards, so re-source envsetup.sh after syncing.
__pocketlock_apply_patches() {
    local top root patch rel project
    top="$(gettop)"
    root="$top/vendor/pocketlock/patches"
    while IFS= read -r patch; do
        rel="${patch#"$root"/}"
        project="${rel%/*}"
        git -C "$top/$project" apply --reverse --check "$patch" >/dev/null 2>&1 && continue
        if git -C "$top/$project" -c user.name=vendor_pocketlock -c user.email=vendor_pocketlock@localhost \
                am -q --no-gpg-sign "$patch" >/dev/null 2>&1; then
            echo "vendor/pocketlock: applied $rel"
        else
            git -C "$top/$project" am --abort >/dev/null 2>&1
            echo "vendor/pocketlock: FAILED to apply $rel" >&2
        fi
    done < <(find "$root" -type f -name '*.patch' | sort)
}
__pocketlock_apply_patches
unset -f __pocketlock_apply_patches
