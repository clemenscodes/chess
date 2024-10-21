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
  in {
    packages = {
      ${system} = {
        default = pkgs.chess;
      };
    };
    devShells = {
      ${system} = {
        default = pkgs.mkShell {
          JAVA_HOME = "${pkgs.jdk.home}";
          buildInputs = [
            pkgs.maven
            pkgs.jdk21
            pkgs.nodejs
            pkgs.pnpm
          ];
        };
      };
    };
  };
}
