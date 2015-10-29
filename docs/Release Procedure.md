Release procedure
=================

When the develop branch is in an release ready state, follow this procedure to create a new release.

1. Decide on a release version number.
    * Large overhauls: increment the first number and reset the rest. E.g., 1.5 becomes 2.0.
    * Significant changes that add features, increment the second number. E.g., 1.5 becomes 1.6.
1. Check out a new branch from develop. Name it `release/[version number]`.
1. Search for all Java files not containing a `@since` Javadoc tag, and add a `@since` tag with the version number.
1. Merge the release branch into `master` and `develop`.
1. Delete the release branch.
