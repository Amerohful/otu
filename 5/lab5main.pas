unit lab5main;
//рисунок 14
interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, TeEngine, Series, ExtCtrls, TeeProcs, Chart, ComCtrls;

type
  TForm1 = class(TForm)
    PageControl1: TPageControl;
    TabSheet1: TTabSheet;
    Chart1: TChart;
    Graphic2: TFastLineSeries;
    Graphic3: TFastLineSeries;
    Series1: TFastLineSeries;
    Series2: TFastLineSeries;
    procedure FormCreate(Sender: TObject);
  private     { Private declarations }
  public      { Public declarations }
  end;

var
  Form1: TForm1;

implementation

{$R *.dfm}
//////////////////////////////////////////////////
///  ПРОЦЕДУРА МОДЕЛИРОВАНИЯ ПЕРЕХОДНЫХ ПРОЦЕССОВ
//////////////////////////////////////////////////
procedure TForm1.FormCreate(Sender: TObject);
////////////////////////////////////////////
   Const g=1.0;

 Var   lin, t1, t2,dz, q,  t3, k10,k20,k30, dt, y,y1,y2,y3,x,z1,z2, z20, z3, k1, k2, k3, k4, m1, m2, m3, m4, std,tau, ns:real;
       i,j:integer;
       mas:array[1..100000] of real;

  begin
     lin:=50;   dt:=0.001;
     z1:=0; z2:=0;z3:=0;
     std:=0.0;
     k10:=10;
 k20:=2;
 k30:=0.1;
 t1:=0.5;
 t2:=0.2;
 t3:=1;
 dz:=0.2;
 q:=1/t2;
     tau:=0.97; //важно для запаздывания
     ns:=tau/dt;               //важно для запаздывания
     i:=0;j:=0;                //важно для запаздывания

      while std<lin do
        begin
          std:=std+dt;

          y1:=z1;    //1 звено
  x:=g-y;
  k1:=(k10/t1*x-y1/t1)*dt;
  k2:=(k10/t1*x-(y1+k1/2)/t1)*dt;
  k3:=(k10/t1*x-(y1+k2/2)/t1)*dt;
  k4:=(k10/t1*x-(y1+k3)/t1)*dt;
  z1:=z1+(k1+2*k2+2*k3+k4)/6;

  y2:=z2;   //2 звено
  k1:=(-2*dz*q*y2+z20)*dt;
  m1:=(k20*q*q*y1-q*q*y2)*dt;
  k2:=(-2*dz*q*(y2+k1/2)+(z20+m1/2))*dt;
  m2:=(k20*q*q*y1-q*q*(y2+k1/2))*dt;
  k3:=(-2*dz*q*(y2+k2/2)+(z20+m2/2))*dt;
  m3:=(k20*q*q*y1-q*q*(y2+k2/2))*dt;
  k4:=(-2*dz*q*(y2+k3)+(z20+m3))*dt;
  m4:=(k20*q*q*y1-q*q*(y2+k3))*dt;
  z2:=z2+(k1+2*k2+2*k3+k4)/6;
  z20:=z20+(m1+2*m2+2*m3+m4)/6;

  y3:=z3;   //3 звено
  k1:=(k30/t3*y2-y3/t3)*dt;
  k2:=(k30/t3*y2-(y3+k1/2)/t3)*dt;
  k3:=(k30/t3*y2-(y3+k2/2)/t3)*dt;
  k4:=(k30/t3*y2-(y3+k3)/t3)*dt;
  z3:=z3+(k1+2*k2+2*k3+k4)/6;
               //Запаздывание
          i:=i+1;
          if(i>ns) then
            begin
               if(j>=ns) then j:=0;
               j:=j+1;
               y:=mas[j];
               mas[j]:=y3;
             end
           else
               begin
                   mas[i]:=y3;
                   y:=0;
                end;
  Series1.AddXY(std,y);
 //  Series2.AddXY(std,y3);  //график без запаздывания
  Graphic2.AddXY(std,0.6666*0.95);  //входное воздействие
  Graphic3.AddXY(std,0.6666*1.05);  //график с учетом запаздывания
          end;
          Form1.Caption:=floattostr(y);
//КОНЕЦ МОДЕЛИРОВАНИЯ
////////////////////////////////////////////

end;

end.

