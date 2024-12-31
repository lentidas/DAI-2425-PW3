locals {
  app_name = "gpg-keyserver"

  default_tags = {
    app        = local.app_name
    project    = "DAI-2425-PW3"
    repository = "https://github.com/lentidas/DAI-2425-PW3"
  }
}
