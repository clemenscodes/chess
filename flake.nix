{
  inputs = {
    nixpkgs = {
      url = "github:NixOS/nixpkgs/nixos-unstable";
    };
  };
  outputs = {nixpkgs, ...}: let
    system = "x86_64-linux";
    pkgs = import nixpkgs {
      inherit system;
      overlays = [
        (final: prev: {
          chess = final.callPackage ./chess.nix {};
        })
      ];
      config = {
        allowUnfreePredicate = pkg: let
          unfreePkgs = [];
        in
          builtins.elem (pkgs.lib.getName pkg) unfreePkgs;
      };
    };
    jdk = pkgs.jdk21;
  in {
    packages = {
      ${system} = rec {
        inherit (pkgs) chess;
        default = chess;
      };
    };
    devShells = {
      ${system} = {
        default = pkgs.mkShell {
          JAVA_HOME = "${jdk.home}";
          buildInputs = [
            jdk
            pkgs.maven
            pkgs.nodejs
            pkgs.pnpm
            pkgs.jdt-language-server
          ];
        };
      };
    };
  };
}
