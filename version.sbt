import scala.sys.process._
// See also https://hbcdigital.atlassian.net/browse/WAM-344
version in ThisBuild := ("git describe --tags --dirty --always" #|| "echo UNVERSIONED").!!(ProcessLogger(_ => ())).trim
