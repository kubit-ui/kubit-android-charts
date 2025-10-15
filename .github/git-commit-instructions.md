# Commit Policy

## Objective

Establish a clear policy for writing commit messages in this repository to improve traceability, understanding, and
quality of the change history. All developers must follow these guidelines when making commits.

## General Rules

1. **Language:**  
   Commit messages must be written in clear and professional English.

2. **Message format:**
    - The _title_ should be brief (maximum 50 characters), written in present tense, and describe the action (example:
      `Fix login validation` or `Add error logging to API calls`).
    - The _body_ (optional) should explain the "what" and "why" of the change, especially if the commit is complex.
    - Use blank lines to separate the title from the message body.

3. **Reference to issues/tickets:**  
   If the commit is related to an issue, task, or ticket, include it in the body using the format:  
   `Related to [XXXX]`, the ticket is obtained from part of the branch name

4. **Atomic commits:**  
   Each commit should represent a logical and self-contained change. Avoid mixing bug fixes with new features in the
   same commit.

5. **Recommended prefixes:**  
   Use prefixes to identify the type of change:
    - `[FIX] -` for bug fixes.
    - `[FEAT] -` for new features.
    - `[REFACTOR] -` for internal improvements without functional changes.
    - `[DOCS] -` for documentation changes.
    - `[TEST] -` for test changes.
    - `[CHORE] -` for minor tasks (updates, configuration, etc).

   Example:  
   `[FIX] - Fix error in user validation`

## Commit message example

```
[FEAT]: Add pagination logic to customer listing

Pagination is implemented to improve performance when loading customers.
Related to [XXXX]
```

## Verification tools

It is recommended to use commit message linting tools,
such as [commitlint](https://github.com/conventional-changelog/commitlint), to ensure compliance with this policy.

## Review

This policy should be reviewed periodically and updated based on team and project needs.
