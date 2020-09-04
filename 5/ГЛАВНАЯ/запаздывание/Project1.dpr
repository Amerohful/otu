program Project1;

uses
  Forms,
  lab5main in 'lab5main.pas' {Form1};

{$R *.res}

begin
  Application.Initialize;
  Application.CreateForm(TForm1, Form1);
  Application.Run;
end.
