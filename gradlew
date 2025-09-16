#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass any JVM options to Gradle.
DEFAULT_JVM_OPTS=""

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "ERROR: $*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLASSPATH=""
# For Cygwin, ensure paths are in UNIX format before anything is touched.
if ${cygwin} ; then
    [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done

APP_HOME=`dirname "$PRG"`

# For Cygwin, switch paths to Windows format before running java
if ${cygwin} ; then
    APP_HOME=`cygpath --path --windows "$APP_HOME"`
    CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

# Add a semi-colon at the end of the classpath if it does not already have one
if [ -n "$CLASSPATH" -a "${CLASSPATH##*;}" = "$CLASSPATH" ]; then
   CLASSPATH="$CLASSPATH;"
fi

# Set a few default variables
#
# When run from the command line, the following variables are honoured:
#
#   JAVA_HOME       (Optional) The location of a JDK to use.
#
#   JAVA_OPTS       (Optional) Any additional options to pass to the JVM.
#
#   GRADLE_HOME     (Optional) The location of a Gradle installation.
#                   If not specified, this script will attempt to downloaded it.
#
#   GRADLE_OPTS     (Optional) Any additional options to pass to Gradle.
#
#   GRADLE_USER_HOME (Optional) The location of the Gradle user home directory.
#                    Defaults to ~/.gradle.
#
#   APP_HOME        (Internal) The location of the script.
#
# A more robust way to find JAVA_HOME
if [ -z "$JAVA_HOME" ]; then
    # Try to find java in path
    JAVA_IN_PATH=$(which java 2>/dev/null)
    if [ -n "$JAVA_IN_PATH" ]; then
        # Resolve symlinks
        JAVA_IN_PATH=$(readlink -f "$JAVA_IN_PATH")
        # Get the directory of the java executable
        JAVA_DIR=$(dirname "$JAVA_IN_PATH")
        # Go up two levels to find the JDK home
        CANDIDATE_JAVA_HOME=$(dirname "$JAVA_DIR")
        if [ -f "$CANDIDATE_JAVA_HOME/bin/javac" ]; then
            JAVA_HOME=$CANDIDATE_JAVA_HOME
        fi
    fi
fi

if [ -z "$JAVA_HOME" ] ; then
    # Fallback for systems where which/readlink may not be ideal
    if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
        JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"
    elif [ -d "/usr/lib/jvm/java-11-openjdk-amd64" ]; then
        JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64"
    fi
fi
if [ -z "$JAVA_HOME" ] ; then
    die "Neither JAVA_HOME nor JDK_HOME is set and we could not find an installed JDK."
fi

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum number of open files
if [ "$nonstop" = "false" ] ; then
    if [ "$darwin" = "true" ] ; then
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then
            # Use the hard limit
            MAX_FD_LIMIT=`ulimit -H -n`
        else
            MAX_FD_LIMIT="$MAX_FD"
        fi

        # OS X is preventing us from raising the file limit too high.
        # See https://github.com/gradle/gradle/issues/3236
        if [ "$MAX_FD_LIMIT" -gt 10240 ] ; then
            warn "High file limit ($MAX_FD_LIMIT) might not be respected by OS X. See https://github.com/gradle/gradle/issues/3236"
        fi

        # Only increasing the file limit if the current value is lower than the new limit.
        # They are both numeric values so we can use '-lt'.
        # shellcheck disable=SC3020
        if [ `ulimit -n` -lt "$MAX_FD_LIMIT" ] ; then
            ulimit -n "$MAX_FD_LIMIT"
        fi
    else
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" = "max" ] ; then
            # Use the hard limit
            # shellcheck disable=SC3045
            if ulimit -H -n > /dev/null 2>&1 ; then
                MAX_FD_LIMIT=`ulimit -H -n`
            else
                MAX_FD_LIMIT="$MAX_FD"
            fi
        else
            MAX_FD_LIMIT="$MAX_FD"
        fi
        # Only increasing the file limit if the current value is lower than the new limit.
        # They are both numeric values so we can use '-lt'.
        # shellcheck disable=SC3020
        if [ `ulimit -n` -lt "$MAX_FD_LIMIT" ] ; then
             # shellcheck disable=SC3045
             if ! ulimit -n "$MAX_FD_LIMIT" > /dev/null 2>&1 ; then
                warn "Could not set maximum file descriptor limit of $MAX_FD_LIMIT"
             fi
        fi
    fi
fi

# Add the jar to the classpath
if [ -z "$GRADLE_WRAPPER_JAR" ] ; then
    # Try to guess GRADLE_WRAPPER_JAR from the wrapper properties
    if [ -z "$APP_HOME" ] ; then
        die "The script isn't in a directory, so we can't determine the wrapper jar location."
    fi
    WRAPPER_PROPS="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"
    if [ -f "$WRAPPER_PROPS" ] ; then
        # shellcheck disable=SC2006
        DISTRIBUTION_URL=`grep "distributionUrl" "$WRAPPER_PROPS" | sed -e 's/\\//g' | sed -e 's/distributionUrl=//'`
        if [ -n "$DISTRIBUTION_URL" ] ; then
            # Expect the wrapper jar to be in the same directory as the properties file
            # Generate the wrapper jar name from the distribution url
            GRADLE_WRAPPER_JAR_NAME=`basename "$DISTRIBUTION_URL" | sed -e 's/\.zip$//'`
            GRADLE_WRAPPER_JAR="$APP_HOME/gradle/wrapper/$GRADLE_WRAPPER_JAR_NAME.jar"
        fi
    fi
fi
if [ -z "$GRADLE_WRAPPER_JAR" ] ; then
    # Fallback to the default jar name
    GRADLE_WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
fi
CLASSPATH="$CLASSPATH$GRADLE_WRAPPER_JAR"

# Add the java agent to GRADLE_OPTS
if [ -n "$JAVA_AGENT_JAR" ] ; then
    GRADLE_OPTS="-javaagent:$JAVA_AGENT_JAR $GRADLE_OPTS"
fi

# Add the profiler args to GRADLE_OPTS
if [ -n "$PROFILER_ARGS" ] ; then
    GRADLE_OPTS="$PROFILER_ARGS $GRADLE_OPTS"
fi

# Split up the JVM options only if the JAVA_OPTS variable is not defined.
# This prevents issues with commands like kill, which are not supported in JAVA_OPTS.
# shellcheck disable=SC2206
if [ -z "$JAVA_OPTS" ] ; then
    JAVA_OPTS=($DEFAULT_JVM_OPTS)
else
    # Mangle the delimiters in JAVA_OPTS to be single spaces and then split the string.
    # This is required to support all of the following JAVA_OPTS styles:
    #   * JAVA_OPTS='-Xms256m -Xmx512m'
    #   * JAVA_OPTS='-Xms256m' '-Xmx512m'
    #   * JAVA_OPTS="-Xms256m -Xmx512m"
    #   * JAVA_OPTS="-Xms256m" "-Xmx512m"
    #   * JAVA_OPTS=(-Xms256m -Xmx512m)
    #   * JAVA_OPTS=(-Xms256m) # with or without quoted values
    #   * JAVA_OPTS=('-Xms256m' '-Xmx512m')
    #   * JAVA_OPTS=("-Xms256m" "-Xmx512m")
    # All of which are valid ways to express JAVA_OPTS in various shells.

    # Step 1: Turn all delimiters into spaces
    # See Stack Overflow explanation for this:
    # https://stackoverflow.com/questions/1469868/spec-compliant-way-to-do-word-splitting-in-shell-scripts#answer-1470295
    #
    # The "trick" here is to use the shell's own parser to do the hard work.
    # We do this by eval'ing a command to echo the words which we then capture.
    # The set -- part avoids issues with values that start with a dash.
    # The "$@" tells the shell to echo all of the words, which were split at the
    # delimiters.
    #
    # The outer `"` characters are important to avoid the shell from
    # word-splitting the result of the command substitution.
    #
    # The default IFS is space, tab, and newline. We will temporarily use
    # a similar set of delimiters to join the arguments with spaces.
    # It is important that we handle the case where the default IFS has been
    # modified by the user.
    #
    # We use `tr` to change all tabs and newlines into spaces.
    # We could have also used `xargs`.
    #
    # In the second step, we'll split the string at the spaces.
    # This is why we use tr to canonicalize the delimiters to spaces.
    canonical_delimiters=$(echo " " | tr " " "\t\n")
    # shellcheck disable=SC2034
    IFS="$canonical_delimiters"
    # Step 1: Echo the user-supplied arguments, which the shell will automatically
    # word-split for us. Then, capture the output.
    #N.B. We preserve the quotes and backslashes for the eval command.
    # shellcheck disable=SC2086,SC2140
    canonical_java_opts_string=$(eval "
        set -- $JAVA_OPTS
        echo \"\$@\"
    ")

    # Step 2: Split the canonical string into an array.
    # This is why we need to use an intermediate variable, because we need to
    # use the default IFS for this.
    # Reset the IFS to its default value
    unset IFS
    # We need to disable the glob feature of the shell to avoid issues with
    # values that contain wildcards.
    set -f
    # shellcheck disable=SC2206
    JAVA_OPTS=($canonical_java_opts_string)
    # Re-enable the glob feature
    set +f
fi

# Now prepend the default JVM options to the JAVA_OPTS array.
#
# We can't use `JAVA_OPTS=($DEFAULT_JVM_OPTS "${JAVA_OPTS[@]}")` because
# of a bug in bash 3.2. See https://github.com/gradle/gradle/issues/17336 for details.
# This is the recommended work-around.
#
# We need to disable the glob feature of the shell to avoid issues with
# values that contain wildcards.
set -f
# shellcheck disable=SC2206
DEFAULT_JVM_OPTS=($DEFAULT_JVM_OPTS)
# Re-enable the glob feature
set +f

# Prepend the default JVM options to the JAVA_OPTS array
#
# This is a bit of a hack, but it works.
# The problem is that we can't use array manipulation in all shells.
# So, we'll convert the arrays to strings and then back to an array.
#
# We need to escape the quotes for the eval command.
#
# Luckily, we are already in a subshell, so we don't need to worry about
# modifying the parent shell's environment.
#
# We must use `printf` instead of `echo` to avoid issues with values that
# start with a dash.
#
# See https://stackoverflow.com/questions/1469868/spec-compliant-way-to-do-word-splitting-in-shell-scripts#answer-1470295
#
# First, convert the arrays to strings
# shellcheck disable=SC2124
default_jvm_opts_string=$(printf '"%s" ' "${DEFAULT_JVM_OPTS[@]}")
# shellcheck disable=SC2124
java_opts_string=$(printf '"%s" ' "${JAVA_OPTS[@]}")

# Second, combine the strings
#N.B. We preserve the quotes and backslashes for the eval command.
# shellcheck disable=SC2086,SC2140
combined_jvm_opts_string=$(eval "echo $default_jvm_opts_string $java_opts_string")

# Third, split the combined string into an array
# Disable globbing to avoid issues with wildcards
set -f
# shellcheck disable=SC2206
JAVA_OPTS=($combined_jvm_opts_string)
# Re-enable globbing
set +f

################################################################################
# Actually run the script
################################################################################

# Escape parameters
#
# This is a bit of a hack, but it is necessary to support all of the
# quoting and escaping that the shell does for us.
#
# We will use `printf` to quote the arguments.
#
# See https://stackoverflow.com/questions/1469868/spec-compliant-way-to-do-word-splitting-in-shell-scripts#answer-1470295
#
# We need to iterate over the arguments and quote them.
# The result will be a string that we can pass to `eval`.
#
# We must use a separate variable to store the result, because we can't
# use `eval` in a loop like this:
#
#   for arg in "$@"; do
#       eval "quoted_args=\"\$quoted_args '\$arg'\""
#   done
#
# This is because the `eval` command will be executed in a subshell,
# and the changes to `quoted_args` will be lost.
#
# We can't use `printf` to quote the arguments in a loop like this:
#
#   for arg in "$@"; do
#       quoted_args="$quoted_args $(printf "'%s' " "$arg")"
#   done
#
# because of the way that `printf` handles multiple arguments.
#
# So, we will use a loop to build up the string.
#
# We need to escape the single quotes in the arguments.
# We will use `sed` to do this.
#
# We also need to escape the backslashes.
# We will use `sed` to do this as well.
quoted_args=""
for arg in "$@"; do
    # We need to escape the single quotes and backslashes.
    #
    # We will use a simple `sed` command to do this.
    # The `sed` command will replace all single quotes with `\'` and all
    # backslashes with `\\`.
    #
    # We need to use a temporary variable to store the escaped argument.
    # This is because we can't use `eval` in a loop.
    #
    # We use `printf` to avoid issues with values that start with a dash.
    escaped_arg=$(printf "%s" "$arg" | sed "s/'/'\\\\''/g; s/\\\\/\\\\\\\\/g")
    quoted_args="$quoted_args '$escaped_arg'"
done

# We need to escape the quotes for the `eval` command.
#N.B. We preserve the quotes and backslashes for the `eval` command.
# shellcheck disable=SC2140
eval "set -- $quoted_args"

# Launch the JVM
exec "$JAVACMD" "${JAVA_OPTS[@]}" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
