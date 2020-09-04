object Form1: TForm1
  Left = -4
  Top = 210
  Width = 696
  Height = 480
  Caption = 'MainForm'
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  Position = poDesktopCenter
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object PageControl1: TPageControl
    Left = 0
    Top = 0
    Width = 680
    Height = 441
    ActivePage = TabSheet1
    Align = alClient
    TabOrder = 0
    object TabSheet1: TTabSheet
      Caption = 'TabSheet1'
      object Chart1: TChart
        Left = 0
        Top = 0
        Width = 672
        Height = 413
        BackWall.Brush.Color = clWhite
        BackWall.Brush.Style = bsClear
        Gradient.EndColor = clWhite
        Gradient.Visible = True
        Title.Text.Strings = (
          'TChart')
        Title.Visible = False
        BottomAxis.ExactDateTime = False
        BottomAxis.Increment = 0.100000000000000000
        BottomAxis.Title.Caption = 't'
        LeftAxis.ExactDateTime = False
        LeftAxis.Increment = 0.100000000000000000
        LeftAxis.Title.Caption = 'y(t),w(t)'
        Legend.Visible = False
        View3D = False
        Align = alClient
        TabOrder = 0
        object Graphic2: TFastLineSeries
          Marks.ArrowLength = 8
          Marks.Visible = False
          SeriesColor = clGreen
          LinePen.Color = clGreen
          XValues.DateTime = False
          XValues.Name = 'X'
          XValues.Multiplier = 1.000000000000000000
          XValues.Order = loAscending
          YValues.DateTime = False
          YValues.Name = 'Y'
          YValues.Multiplier = 1.000000000000000000
          YValues.Order = loNone
        end
        object Graphic3: TFastLineSeries
          Marks.ArrowLength = 8
          Marks.Visible = False
          SeriesColor = 8404992
          LinePen.Color = 8404992
          XValues.DateTime = False
          XValues.Name = 'X'
          XValues.Multiplier = 1.000000000000000000
          XValues.Order = loAscending
          YValues.DateTime = False
          YValues.Name = 'Y'
          YValues.Multiplier = 1.000000000000000000
          YValues.Order = loNone
        end
        object Series1: TFastLineSeries
          Marks.ArrowLength = 8
          Marks.Visible = False
          SeriesColor = clRed
          Title = 'Graphic1'
          LinePen.Color = clRed
          XValues.DateTime = False
          XValues.Name = 'X'
          XValues.Multiplier = 1.000000000000000000
          XValues.Order = loAscending
          YValues.DateTime = False
          YValues.Name = 'Y'
          YValues.Multiplier = 1.000000000000000000
          YValues.Order = loNone
        end
      end
    end
  end
end
