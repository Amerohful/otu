unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, TeEngine, Series, ExtCtrls, TeeProcs, Chart, StdCtrls;

type
  TForm1 = class(TForm)
    Chart1: TChart;
    Series1: TFastLineSeries;
    Series2: TFastLineSeries;
    Series3: TFastLineSeries;
    Series4: TFastLineSeries;
    Series5: TFastLineSeries;
    StaticText1: TStaticText;
    procedure FormCreate(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}

procedure TForm1.FormCreate(Sender: TObject);

const T1=0.025; C3=7;//параметры

l=3; //интервал

dt=0.00001; //шаг

var y, y1, y2, z1, z2,c2, z3, x, t2, t3, std, y_max, tp, tn,

k, k1, k2, k3, k4,k10,k20,k30,f1,f2,g,krit: real;

begin

krit:=0; y:=0; k:=0; k10:=5.625; k20:=3.5; k30:=1.3; f1:=0; f2:=0; c2:=sqrt(c3);

t3:=0.1; z1:=0; z2:=0; z3:=0; std:=0; y_max:=0; tp:=0; tn:=0;

while std<=L do

begin

g:=1; //Моделирование

y1:=z1; //1 звено //метод Рунге-Кутта

x:=g-y;

k1:=(k10/t1*x-y1/t1)*dt;

k2:=(k10/t1*x-(y1+k1/2)/t1)*dt;

k3:=(k10/t1*x-(y1+k2/2)/t1)*dt;

k4:=(k10/t1*x-(y1+k3)/t1)*dt;

z1:=z1+(k1+2*k2+2*k3+k4)/6;

y2:=z2; //2 звено

k1:=(k20*(y1+f1))*dt;

k2:=(k20*(y1+f1))*dt;

k3:=(k20*(y1+f1))*dt;

k4:=(k20*(y1+f1))*dt;

z2:=z2+(k1+2*k2+2*k3+k4)/6;

y:=z3; //3 звено

k1:=(k30/t3*(y2+F2)-y/t3)*dt;

k2:=(k30/t3*(y2+F2)-(y+k1/2)/t3)*dt;

k3:=(k30/t3*(y2+F2)-(y+k2/2)/t3)*dt;

k4:=(k30/t3*(y2+F2)-(y+k3)/t3)*dt;

z3:=z3+(k1+2*k2+2*k3+k4)/6;

krit:=krit+x*x*dt;

if y > y_max  then
  y_max := y;

if y >= 0.97 then if y <= 1.02 then if tn = 0 then
  tn:=std;

if y >= 0.95 then if y <= 1.05 then if tp = 0 then
  tp:=std
else
  tp:=0;


Series1.AddXY(std,y); //вывод графиков

//Series2.AddXY(std,0.95);

// Series3.AddXY(std,1.05);

//Series2.AddXY(std,y/2);

//Series4.AddXY(std,g); //переходная для графика

//Series3.AddXY(std,x);

std:=std+dt;

end;

AllocConsole;
writeln(Concat('y_max = ', floattostr(y_max)));
writeln(Concat('tn = ', floattostr(tn)));
writeln(Concat('tp = ', floattostr(tp)));
Application.Run;

statictext1.caption:=floattostr(y);

end;

end.
