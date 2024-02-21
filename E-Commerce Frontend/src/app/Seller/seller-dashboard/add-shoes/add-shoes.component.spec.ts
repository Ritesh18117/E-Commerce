import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddShoesComponent } from './add-shoes.component';

describe('AddShoesComponent', () => {
  let component: AddShoesComponent;
  let fixture: ComponentFixture<AddShoesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddShoesComponent]
    });
    fixture = TestBed.createComponent(AddShoesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
