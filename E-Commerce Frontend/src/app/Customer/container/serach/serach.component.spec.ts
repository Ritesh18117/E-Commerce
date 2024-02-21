import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SerachComponent } from './Serach.component';

describe('SerachComponent', () => {
  let component: SerachComponent;
  let fixture: ComponentFixture<SerachComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SerachComponent]
    });
    fixture = TestBed.createComponent(SerachComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
