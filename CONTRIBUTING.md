## Contributing

We are open to contributions. If you want to contribute to the project, please follow the steps below:

### Development Workflow

1. **Fork the Repository**: Click the "Fork" button in the upper right corner of the repository's page on GitHub. This will create a copy of the repository in your account.

2. **Clone the Repository**: Use `git clone` to clone the repository to your local machine.

   ```sh
   git clone https://github.com/kubit-ui/kubit-android-charts.git
   ```

3. **Create a Branch**: Use proper branch naming conventions for automatic version detection.

   ```sh
   git checkout -b <branch-type>/<branch-name>
   ```

4. **Make Changes**: Make any necessary changes to the codebase and **test** the changes thoroughly.

5. **Commit Changes**: Use conventional commit messages when possible.

   ```sh
   git commit -m "feat: add new component feature"
   ```

6. **Push Changes**: Use `git push` to push your changes to your forked repository.

   ```sh
   git push origin <branch-name>
   ```

7. **Open a Pull Request**: Go to the original repository on GitHub and click the "New pull request" button. Fill out the form with details about your changes and submit the pull request.

### Branch Naming & Automatic Publishing

This repository uses an **automatic publishing system** that determines the version bump based on your branch name and PR content. When your PR is merged, the package will be automatically published to NPM.

#### Branch Naming Patterns

Use these branch prefixes to ensure automatic publishing works correctly:

| Branch Pattern | Version Bump | Example | Description |
|----------------|--------------|---------|-------------|
| `feat/` or `feature/` | **MINOR** | `feat/add-tooltip` | New features or enhancements |
| `fix/` or `bugfix/` | **PATCH** | `fix/button-hover-state` | Bug fixes |
| `break/` or `breaking/` | **MAJOR** | `break/remove-old-api` | Breaking changes |
| `hotfix/` | **PATCH** | `hotfix/critical-security-fix` | Urgent fixes |
| `chore/` | **PATCH** | `chore/update-dependencies` | Maintenance tasks |

#### Advanced Version Detection

The system also analyzes your **PR title** and **description** for more precise version detection:

##### MAJOR (Breaking Changes)
- `BREAKING CHANGE:` in PR description
- `!` in PR title (e.g., `feat!: redesign button API`)
- `[breaking]` tag in PR title
- Conventional commits with `!` (e.g., `feat(api)!: change interface`)

##### MINOR (New Features)
- PR titles starting with `feat:` or `feature:`
- `[feature]` tag in PR title
- Conventional commits like `feat(ui): add dark mode`

##### PATCH (Bug Fixes & Others)
- PR titles starting with `fix:` or `bugfix:`
- All other changes (default behavior)
- Conventional commits like `fix(button): hover state`

#### Examples

**Adding a new feature:**
```sh
git checkout -b feat/dark-mode-support
# Make your changes
git commit -m "feat(theme): add dark mode support for all components"
# Create PR with title: "feat(theme): add dark mode support"
# Result: MINOR version bump (e.g., 1.0.0 → 1.1.0)
```

**Fixing a bug:**
```sh
git checkout -b fix/button-accessibility
# Make your changes  
git commit -m "fix(button): improve keyboard navigation"
# Create PR with title: "fix(button): improve keyboard navigation"
# Result: PATCH version bump (e.g., 1.0.0 → 1.0.1)
```

**Breaking changes:**
```sh
git checkout -b break/remove-deprecated-props
# Make your changes
git commit -m "feat!: remove deprecated size prop from Button"
# Create PR with title: "feat!: remove deprecated size prop"
# PR description: "BREAKING CHANGE: The 'size' prop has been removed..."
# Result: MAJOR version bump (e.g., 1.0.0 → 2.0.0)
```